package com.tapac1k.data.entities.trainingset

import androidx.room.*

@Dao
interface TrainingSetDao {
    @Transaction
    suspend fun saveTrainingSets(trainingId: Long, sets: List<TrainSetEntity>) {
        removeAllSetsFromTraining(trainingId)
        insertAll(sets)
    }

    @Query("DELETE FROM training_set WHERE parent_training_id = :trainingId")
    suspend fun removeAllSetsFromTraining(trainingId: Long)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(sets: List<TrainSetEntity>)
}