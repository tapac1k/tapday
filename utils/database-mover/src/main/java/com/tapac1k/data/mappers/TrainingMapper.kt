package com.tapac1k.data.mappers

import com.tapac1k.data.entities.training.FullTrainingEntity
import com.tapac1k.domain.entities.Training
import javax.inject.Inject

class TrainingMapper @Inject constructor() {
    fun map(
        entity: FullTrainingEntity,
        trainingSetMapper: TrainingSetMapper,
        exerciseMapper: ExerciseMapper
    ): Training {
        return with(entity) {
            Training(
                trainingEntity.id,
                trainingEntity.description,
                trainingEntity.date,
                trainingSets.map { trainingSetMapper.map(it, exerciseMapper) }
            )
        }
    }

    fun map(
        entities: List<FullTrainingEntity>,
        trainingSetMapper: TrainingSetMapper,
        exerciseMapper: ExerciseMapper
    ): List<Training> {
        return entities.map { map(it, trainingSetMapper, exerciseMapper) }
    }
}