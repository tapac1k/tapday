package com.tapac1k.data

import com.tapac1k.data.entities.TagEntity
import com.tapac1k.data.entities.training.TrainingEntity
import com.tapac1k.data.mappers.ExerciseMapper
import com.tapac1k.data.mappers.TrainingMapper
import com.tapac1k.data.mappers.TrainingSetMapper
import com.tapac1k.domain.DatabaseRepository
import com.tapac1k.domain.entities.Exercise
import com.tapac1k.domain.entities.TrainSet
import com.tapac1k.domain.entities.Training
import com.tapac1k.domain.utils.capitalize
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    mainDatabase: MainDatabase,
    private val trainingMapper: TrainingMapper,
    private val trainingSetMapper: TrainingSetMapper,
    private val exerciseMapper: ExerciseMapper
) : DatabaseRepository {
    private val trainingDao = mainDatabase.trainingDao()
    private val exerciseDao = mainDatabase.exerciseDao()
    private val trainSetDao = mainDatabase.trainingSetDao()

    override suspend fun listenExercises(): Flow<List<Exercise>> {
        return exerciseDao.listenExercise().map { exerciseMapper.map(it) }
    }

    override suspend fun listenTags(): Flow<List<String>> {
        return exerciseDao.listenTags()
    }

    override suspend fun addTag(tag: String) {
        exerciseDao.insertTag(TagEntity(tag.capitalize(), -1))
    }

    override suspend fun renameTag(tag: String, newName: String) {
        exerciseDao.renameTag(tag.capitalize(), newName)
    }

    override suspend fun getExercise(id: Long): Exercise {
        return exerciseDao.getExerciseById(id).map { exerciseMapper.map(it) }.first()
    }

    suspend fun deleteExercise(exerciseId: Long) {
        exerciseDao.archiveExercise(exerciseId)
    }
    
    override suspend fun getTrainingsWithExercise(id: Long): List<Training> {
        return trainingDao.getTrainingsWithExercise(id).distinct().let {
            trainingMapper.map(it, trainingSetMapper, exerciseMapper)
        }
    }

    override suspend fun createTraining(): Long {
        println(this)
        val id = System.nanoTime()
        val training = TrainingEntity(id = id, date = System.currentTimeMillis())
        trainingDao.insert(training)
        return id
    }

    override suspend fun setDescription(id: Long, desc: String?) {
        trainingDao.setDescription(id, desc)
    }

    override suspend fun getTraining(id: Long): Training {
        return trainingDao.getTraining(id)
            .let { trainingMapper.map(it, trainingSetMapper, exerciseMapper) }
    }

    override suspend fun getTrainings(): List<Training> {
        TODO("Not yet implemented")
    }

    override suspend fun listenTrainings(): Flow<List<Training>> {
        return trainingDao.subscribeTrainings()
            .map {
                trainingMapper.map(it, trainingSetMapper, exerciseMapper)
            }
    }

    override suspend fun saveTrainingSets(trainingInd: Long, training: List<TrainSet>) {
        trainSetDao.saveTrainingSets(trainingInd, training.map { trainingSetMapper.map(it) })
    }

    override suspend fun saveExercise(exercise: Exercise) {
        exerciseDao.insert(exercise.let { exerciseMapper.map(it) })
        exerciseDao.insertTags(exercise.tags.map { TagEntity(it.capitalize(), -1) })
        exerciseDao.insertTags(exercise.tags.map { TagEntity(it.capitalize(), exercise.id) })
    }

    override suspend fun updateExercise(exercise: Exercise) {
        exerciseDao.update(exercise.let { exerciseMapper.map(it) })
        exerciseDao.removeTagsForExercise(exercise.id)
        exerciseDao.insertTags(exercise.tags.map { TagEntity(it.capitalize(), exercise.id) })
        exerciseDao.insertTags(exercise.tags.map { TagEntity(it.capitalize(), -1) })
    }

    override suspend fun updateTrainingDate(trainingId: Long, newDate: Long) {
        trainingDao.updateDate(trainingId, newDate)
    }

    override suspend fun deleteTraining(trainingId: Long) {
        trainingDao.archiveById(trainingId)
    }

    override suspend fun undeleteTraining(trainingId: Long) {
        trainingDao.unarchiveById(trainingId)
    }

    override suspend fun getArchivedWorkouts(): Flow<List<Training>> {
        return trainingDao.subscribeArchivedTrainings().map {
            trainingMapper.map(it, trainingSetMapper, exerciseMapper)
        }
    }
}