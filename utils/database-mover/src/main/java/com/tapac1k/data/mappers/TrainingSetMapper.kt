package com.tapac1k.data.mappers

import com.tapac1k.data.entities.trainingset.FullTrainSetEntity
import com.tapac1k.data.entities.trainingset.TrainSetEntity
import com.tapac1k.domain.entities.TrainSet
import javax.inject.Inject

class TrainingSetMapper @Inject constructor() {
    fun map(entity: FullTrainSetEntity, exerciseMapper: ExerciseMapper): TrainSet {
        return with(entity) {
            TrainSet(
                index = trainSetEntity.index,
                weight = trainSetEntity.weight,
                exercise = exerciseMapper.map(exerciseEntity),
                trainingId = trainSetEntity.trainingId,
                reps = trainSetEntity.reps,
                time = trainSetEntity.time
            )
        }
    }

    fun map(trainSet: TrainSet): TrainSetEntity {
        return with(trainSet) {
            TrainSetEntity(
                index = index,
                weight = weight,
                trainingId = trainingId,
                reps = reps,
                time = time, 
                exerciseId = exercise.id
            )
        }
    }
}