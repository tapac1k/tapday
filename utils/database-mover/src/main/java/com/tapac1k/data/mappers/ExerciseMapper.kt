package com.tapac1k.data.mappers

import com.tapac1k.data.entities.exercise.ExerciseEntity
import com.tapac1k.data.entities.exercise.FullExerciseEntity
import com.tapac1k.domain.entities.Exercise
import javax.inject.Inject

class ExerciseMapper @Inject constructor() {
    fun map(exerciseEntity: FullExerciseEntity): Exercise {
        return with(exerciseEntity.exerciseEntity) {
            Exercise(id, name, withWeight, type, exerciseEntity.tags.map { it.tag })
        }
    }

    fun map(entities: List<FullExerciseEntity>): List<Exercise> {
        return entities.map(::map)
    }

    fun map(exercise: Exercise): ExerciseEntity {
        return ExerciseEntity(
            exercise.id,
            exercise.name,
            exercise.withWeight,
            exercise.type
        )
    }
}