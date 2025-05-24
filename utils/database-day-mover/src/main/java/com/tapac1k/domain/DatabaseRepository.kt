package com.tapac1k.tapday.domain

import com.tapac1k.tapday.data.db.enitites.day.DayParamEntity
import com.tapac1k.tapday.domain.entities.ParameterType
import com.tapac1k.tapday.domain.entities.body.BodyParamInfo
import com.tapac1k.tapday.domain.entities.body.BodySummary
import com.tapac1k.tapday.domain.entities.day.DayParamInfo
import com.tapac1k.tapday.domain.entities.day.DaySummary
import com.tapac1k.tapday.domain.entities.day.ShortDaySummary
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {
    suspend fun createDayRecordIfNeeded(time: Long = System.currentTimeMillis())
    suspend fun createParameter(title: String, type: ParameterType)
    suspend fun isParamWithSuchTitleExist(title: String): Boolean


    suspend fun getDaySummary(id: Long): DaySummary?
    suspend fun saveDaySummary(daySummary: DaySummary)

    suspend fun getDayParams(): List<DayParamInfo>
    fun listenDayParams(): Flow<List<DayParamInfo>>
    suspend fun createEdible(title: String)
    fun listenEdibles(): Flow<List<String>>
    fun subscribeDaySummaries(): Flow<List<ShortDaySummary>>
    suspend fun deleteParam(id: Long)
    suspend fun restoreParam(id: Long)
    fun subscribeBodyParams(): Flow<List<BodyParamInfo>>
    suspend fun createBodyParam(title: String)
    suspend fun saveBodySummary(bodySummary: BodySummary)
    suspend fun createBodySummary(id: Long)
    suspend fun restoreBodyParam(id: Long)
    suspend fun deleteBodyParam(id: Long)
    suspend fun createBodyRecordIfNeeded(time: Long = System.currentTimeMillis())
    fun subscribeBodySummaries(): Flow<List<Long>>
    suspend fun getBodySummary(id: Long): BodySummary
    suspend fun getAllSummary(): List<DaySummary>
}