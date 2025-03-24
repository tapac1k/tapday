package com.tapac1k.training.data.usecase

import android.content.Context
import android.util.Log
import com.tapac1k.domain.entities.TrainSet
import com.tapac1k.getDatabaseRepository
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract.SyncDatabaseWithFirebase
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.domain.TrainingService
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import kotlin.math.roundToInt

class SyncDatabaseWithFirebaseImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val trainingService: TrainingService,
) : SyncDatabaseWithFirebase {

    override suspend fun invoke() {
        val databaseRepository = getDatabaseRepository(context)
        val trainings = databaseRepository.listenTrainings().first()
        Log.d("TestX", "trainings: ${trainings.count()}")

        val hashMapExercise = mutableMapOf<String, Exercise>()
        val mapTag = mutableMapOf<String, TrainingTag>()
        val dateFormatter = java.text.SimpleDateFormat("dd/MM/yyyy")
        suspend fun getTag(tag1: String): TrainingTag {
            val tag = tag1.trim().lowercase()
            return mapTag.getOrPut(tag) {
                trainingService.createTrainingTag(tag).onSuccess {
                    Log.d("TestX", "saved tag ${it}")
                }.getOrThrow()
            }
        }

        suspend fun getExercise(ex: com.tapac1k.domain.entities.Exercise): Exercise {
            return hashMapExercise.getOrPut(ex.id.toString()) {
                trainingService.saveExercise(
                    Exercise(
                    id = ex.id.toString(),
                    name = ex.name,
                    withWeight = ex.withWeight,
                    timeBased = ex.type == com.tapac1k.domain.entities.Exercise.Type.TIME,
                    tags = ex.tags.map {
                        getTag(it)
                    }
                )).onSuccess {
                    Log.d("TestX", "saved exericise ${it.name}")
                }.getOrThrow()
            }
        }
        trainings.forEachIndexed { index, training ->
            val exerciseGroupSets = mutableListOf<TrainSet>()
            val exerciseGroups = mutableListOf<ExerciseGroup>()
            for (set in training.sets) {
                if (exerciseGroupSets.isNotEmpty() && set.exercise != exerciseGroupSets.last().exercise) {
                    exerciseGroups.add(
                        ExerciseGroup(
                            id = "" + System.currentTimeMillis() + exerciseGroups.size,
                            exercise = getExercise(exerciseGroupSets.last().exercise),
                            sets = exerciseGroupSets.map {
                                ExerciseSet(
                                    "" + System.currentTimeMillis() + it.index,
                                    reps = it.reps.takeIf { it >= 0 },
                                    weight = it.weight.takeIf { it >= 0 },
                                    time = it.time.roundToInt().takeIf { it >= 0 },
                                    )
                            }
                        )
                    )
                    exerciseGroupSets.clear()
                }
                exerciseGroupSets.add(
                    set
                )
            }
            if (exerciseGroupSets.isNotEmpty()) {
                exerciseGroups.add(
                    ExerciseGroup(
                        id = "" + System.currentTimeMillis() + exerciseGroups.size,
                        exercise = getExercise(exerciseGroupSets.last().exercise),
                        sets = exerciseGroupSets.map {
                            ExerciseSet(
                                "" + System.currentTimeMillis() + it.index,
                                reps = it.reps.takeIf { it >= 0 },
                                weight = it.weight.takeIf { it >= 0 },
                                time = it.time.roundToInt().takeIf { it >= 0 },
                                )
                        }
                    )
                )
            }
            trainingService.saveTraining(
                training.id.toString(),
                exerciseGroups,
                training.date,
                training.description.orEmpty()
            ).onSuccess {
                Log.d("TestX", "saved training for date ${dateFormatter.format(training.date)}")
            }
        }
        Log.d("TestX", "sync finished")
    }
}