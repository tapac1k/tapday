package com.tapac1k.data.entities.training

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
abstract class TrainingDao {
    @Insert
    abstract suspend fun insert(trainingEntity: TrainingEntity)

    @Transaction
    @Query("SELECT * FROM training WHERE archived != 1 ORDER BY date DESC")
    abstract fun subscribeTrainings(): Flow<List<FullTrainingEntity>>
    @Transaction
    @Query("SELECT * FROM training WHERE archived == 1 ORDER BY date DESC")
    abstract fun subscribeArchivedTrainings(): Flow<List<FullTrainingEntity>>

    @Transaction
    @Query("SELECT * FROM training WHERE training_id = :id")
    abstract suspend fun getTraining(id: Long): FullTrainingEntity

    @Transaction
    @Query("SELECT * FROM training_set, training ON training_id = parent_training_id WHERE exercise_id = :exerciseId")
    abstract suspend fun getTrainingsWithExercise(exerciseId: Long): List<FullTrainingEntity>

    @Query("UPDATE training SET date = :newDate WHERE training_id = :trainingId")
    abstract suspend fun updateDate(trainingId: Long, newDate: Long)

    @Query("UPDATE training SET archived = 1 WHERE training_id = :trainingId")
    abstract suspend fun archiveById(trainingId: Long)
    @Query("UPDATE training SET archived = 0 WHERE training_id = :trainingId")
    abstract suspend fun unarchiveById(trainingId: Long)

    @Query("UPDATE training SET description = :description WHERE training_id = :trainingId")
    abstract suspend fun setDescription(trainingId: Long, description: String?)
}