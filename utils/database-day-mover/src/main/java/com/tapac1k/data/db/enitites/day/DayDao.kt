package com.tapac1k.tapday.data.db.enitites.day

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
    @Insert
    suspend fun insertDayEntity(daySummaryEntity: DaySummaryEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createParam(dayParamEntity: DayParamEntity)

    @Insert
    suspend fun insertRecord(dayParamRecordEntity: DayParamRecordEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecords(dayParamRecordEntities: List<DayParamRecordEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertEdibles(dayParamRecordEntities: List<EdibleEntity>)

    @Update
    suspend fun updateDaySummary(daySummaryEntity: DaySummaryEntity)

    @Query("SELECT * FROM day_param WHERE title = :title")
    suspend fun getDayParamByTitle(title: String): DayParamEntity?

    @Query("SELECT EXISTS(SELECT * FROM day_summary WHERE day_summary_id = :id)")
    suspend fun isDayEntityExist(id: Long): Boolean

    @Query("SELECT * FROM day_param WHERE deleted_at == -1")
    suspend fun getDayParams(): List<DayParamEntity>

    @Query("SELECT * FROM day_param")
    fun listenDayParams(): Flow<List<DayParamEntity>>

    @Query("UPDATE day_param SET deleted_at = :deletedAt WHERE day_param_id = :id")
    suspend fun updateDayParamDeletedState(id: Long, deletedAt: Long)

    @Transaction
    @Query("SELECT * FROM day_summary, day_param_record ON day_summary_id = day_summary_ref")
    suspend fun getDaySummaries(): List<FullDaySummary>

    @Transaction
    @Query("SELECT * FROM day_summary LEFT JOIN " +
        "(SELECT day_summary_ref, sum(value) as overall, type FROM day_param_record, day_param ON day_param_ref = day_param_id GROUP BY day_summary_ref, type) " +
        "ON  day_summary_ref = day_summary_id " +
        "ORDER BY day_summary_id DESC")
    fun subscribeDaySummaries(): Flow<Map<DaySummaryEntity, List<SumInfo>>>

    @Transaction
    @Query("SELECT * FROM day_summary WHERE day_summary_id = :id")
    suspend fun getDaySummary(id: Long):  FullDaySummary?

    @Query("SELECT DISTINCT edible_name FROM edible WHERE consumption_day_id == -1")
    fun subscribeEdibleTitles(): Flow<List<String>>
}