package com.tapac1k.tapday.data.db.enitites.body

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BodyDao {
    @Insert
    suspend fun insertBodyParam(bodyParamEntity: BodyParameterEntity)

    @Query("SELECT EXISTS(SELECT * FROM body_summary WHERE body_summary_id = :id)")
    suspend fun isBodyEntityExist(id: Long): Boolean

    @Insert
    suspend fun insertBodySummary(bodySummaryEntity: BodySummaryEntity)

    @Insert
    suspend fun insertRecord(bodyParamRecordEntity: BodyParameterRecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(dayParamRecordEntities: List<BodyParameterRecordEntity>)

    @Query("SELECT * FROM body_param")
    fun subscribeBodyParams(): Flow<List<BodyParameterEntity>>

    @Query("SELECT * FROM body_summary ORDER BY body_summary_id DESC")
    fun subscribeBodySummaries(): Flow<List<BodySummaryEntity>>

    @Query("UPDATE body_param SET deleted_at = :deletedAt WHERE body_param_id = :id")
    suspend fun updateBodyParamDeletedState(id: Long, deletedAt: Long)

    @Query("SELECT * FROM body_summary WHERE body_summary_id = :id LIMIT 1")
    suspend fun getBodySummary(id: Long): FullBodySummaryEntity

    @Query("DELETE FROM body_record WHERE body_summary_ref = :id")
    suspend fun removeRecordsWithId(id: Long)
}