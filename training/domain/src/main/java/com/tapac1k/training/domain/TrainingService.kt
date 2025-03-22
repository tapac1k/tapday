package com.tapac1k.training.domain

import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.TrainingTag
import kotlinx.coroutines.flow.Flow

interface TrainingService {
    fun getTrainingTags(): Flow<List<TrainingTag>>
    suspend fun createTrainingTag(tag: String): Result<TrainingTag>
    suspend fun editTrainingTag(id: String, value: String): Result<Unit>


    fun getExercises(): Flow<List<Exercise>>
    suspend fun saveExercise(exercise: Exercise): Result<Exercise>
    suspend fun getExerciseDetails(exerciseId: String): Result<Exercise>

    class TagAlreadyExistException(tagName: String) : Exception("Tag $tagName already exist")
    class ExerciseAlreadyExistException(exerciseName: String) : Exception("Tag $exerciseName already exist")
    class FieldShouldNotBeEmpty(fieldName: String) : Exception("Field $fieldName should not be empty")
}