package com.tapac1k.domain

import com.tapac1k.domain.entities.Exercise
import com.tapac1k.domain.entities.TrainSet
import com.tapac1k.domain.entities.Training
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun createTraining(): Long
    suspend fun getTrainings(): List<Training>
    suspend fun getTraining(id: Long): Training
    suspend fun listenTrainings(): Flow<List<Training>>
    suspend fun saveTrainingSets(trainingInd: Long, training: List<TrainSet>)

    suspend fun listenExercises(): Flow<List<Exercise>>
    suspend fun getTrainingsWithExercise(id: Long): List<Training>
    suspend fun saveExercise(exercise: Exercise)
    suspend fun updateTrainingDate(trainingId: Long, newDate: Long)
    suspend fun deleteTraining(trainingId: Long)
    suspend fun setDescription(id: Long, desc: String?)


    suspend fun listenTags(): Flow<List<String>>
    suspend fun addTag(tag: String)
    suspend fun renameTag(tag: String, newName: String)
    suspend fun getExercise(id: Long): Exercise
    suspend fun updateExercise(exercise: Exercise)
    suspend fun undeleteTraining(trainingId: Long)
    suspend fun getArchivedWorkouts(): Flow<List<Training>>
}