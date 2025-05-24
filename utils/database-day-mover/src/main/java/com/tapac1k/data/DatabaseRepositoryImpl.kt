package com.tapac1k.tapday.data

import android.content.Context
import androidx.room.Room
import com.tapac1k.tapday.data.db.enitites.body.BodyParameterEntity
import com.tapac1k.tapday.data.db.enitites.body.BodyParameterRecordEntity
import com.tapac1k.tapday.data.db.enitites.body.BodySummaryEntity
import com.tapac1k.tapday.data.db.enitites.body.mapToDomain
import com.tapac1k.tapday.data.db.enitites.day.DayParamEntity
import com.tapac1k.tapday.data.db.enitites.day.DaySummaryEntity
import com.tapac1k.tapday.data.db.enitites.day.EdibleEntity
import com.tapac1k.tapday.data.db.enitites.day.mapToDomain
import com.tapac1k.tapday.data.db.enitites.day.mapToEntity
import com.tapac1k.tapday.domain.DatabaseRepository
import com.tapac1k.tapday.domain.entities.ParameterType
import com.tapac1k.tapday.domain.entities.body.BodyParamInfo
import com.tapac1k.tapday.domain.entities.body.BodySummary
import com.tapac1k.tapday.domain.entities.day.DayParamInfo
import com.tapac1k.tapday.domain.entities.day.DaySummary
import com.tapac1k.tapday.domain.entities.day.ShortDaySummary
import com.tapac1k.tapquote.data.db.MainDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

fun createDatabaseRepository(context: Context): DatabaseRepository {
    val database = Room.databaseBuilder(context, MainDatabase::class.java, "main_db.db")
        .setTransactionExecutor(Executors.newSingleThreadExecutor())
        .build()
    return DatabaseRepositoryImpl(database)
}

@Singleton
class DatabaseRepositoryImpl @Inject constructor(
    database: MainDatabase,
) : DatabaseRepository {
    var dispatcher: CoroutineDispatcher = Dispatchers.IO
    val dayDao = database.dayDao()
    val bodyDao = database.bodyDao()

    override suspend fun createDayRecordIfNeeded(time: Long) = withContext(dispatcher) {
        val dayLong = TimeUnit.MILLISECONDS.toDays(time)
        val dayEntity = DaySummaryEntity(
            dayLong,
            editedAt = time
        )
        if (!dayDao.isDayEntityExist(dayLong)) {
            dayDao.insertDayEntity(dayEntity)
        }
    }

    override suspend fun createBodyRecordIfNeeded(time: Long) = withContext(dispatcher) {
        val dayLong = TimeUnit.MILLISECONDS.toDays(time)
        val dayEntity = BodySummaryEntity(
            dayLong,
            editedAt = time,
            deletedAt = -1L
        )
        if (!bodyDao.isBodyEntityExist(dayLong)) {
            bodyDao.insertBodySummary(dayEntity)
        }
    }


    override suspend fun createParameter(title: String, type: ParameterType) =
        withContext(dispatcher) {
            dayDao.createParam(
                DayParamEntity(
                    title = title.trim().capitalize(),
                    type = type,
                )
            )
        }

    override suspend fun isParamWithSuchTitleExist(title: String): Boolean {
        return dayDao.getDayParamByTitle(title.trim().capitalize()) != null
    }

    override suspend fun getDaySummary(id: Long): DaySummary = withContext(dispatcher) {
        dayDao.getDaySummary(id)!!.mapToDomain()
    }

    override fun subscribeDaySummaries(): Flow<List<ShortDaySummary>> {
        return dayDao.subscribeDaySummaries().flowOn(dispatcher)
            .map {
                it.map {
                    ShortDaySummary(
                        it.key.id,
                        it.key.happiness,
                        it.key.sleepHours,
                        it.value.filter { it.type.isGood() }.sumOf { it.overall },
                        it.value.filter { !it.type.isGood() }.sumOf { it.overall },
                    )
                }
            }
    }

    override suspend fun saveDaySummary(daySummary: DaySummary) = withContext(dispatcher) {
        val dayEntity = daySummary.mapToEntity()
        val recordEntities = daySummary.parameters.map { it.mapToEntity(daySummary.id) }
        dayDao.updateDaySummary(dayEntity)
        dayDao.insertRecords(recordEntities)
        dayDao.insertEdibles(daySummary.edibles.map { EdibleEntity(it, daySummary.id) })
    }

    override suspend fun getDayParams(): List<DayParamInfo> = withContext(dispatcher) {
        dayDao.getDayParams().map { it.mapToDomain() }
    }

    override fun listenDayParams(): Flow<List<DayParamInfo>> {
        return dayDao.listenDayParams().flowOn(dispatcher).map { it.map { it.mapToDomain() } }
    }

    override suspend fun createEdible(title: String) = withContext(dispatcher) {
        dayDao.insertEdibles(listOf(EdibleEntity(title, -1L)))
    }

    override fun listenEdibles(): Flow<List<String>> {
        return dayDao.subscribeEdibleTitles().flowOn(dispatcher)
    }

    override suspend fun deleteParam(id: Long) = withContext(dispatcher) {
        dayDao.updateDayParamDeletedState(id, System.currentTimeMillis())
    }

    override suspend fun restoreParam(id: Long) = withContext(dispatcher) {
        dayDao.updateDayParamDeletedState(id, -1L)
    }

    override suspend fun createBodySummary(id: Long) = withContext(dispatcher) {
        bodyDao.insertBodySummary(BodySummaryEntity(id, System.currentTimeMillis(), -1))
    }

    override suspend fun saveBodySummary(bodySummary: BodySummary) = withContext(dispatcher) {
        bodyDao.removeRecordsWithId(bodySummary.id)
        bodyDao.insertRecords(bodySummary.params.map {
            BodyParameterRecordEntity(
                bodySummary.id,
                it.id,
                it.value
            )
        })
    }

    override suspend fun createBodyParam(title: String) = withContext(dispatcher) {
        if (title.isBlank()) error("Cannot be blank")
        bodyDao.insertBodyParam(BodyParameterEntity(title = title.trim().capitalize()))
    }

    override fun subscribeBodyParams(): Flow<List<BodyParamInfo>> {
        return bodyDao.subscribeBodyParams().flowOn(dispatcher)
            .map { it.map { BodyParamInfo(it.id, it.title, it.deletedAt, it.editedAt) } }
    }

    override fun subscribeBodySummaries(): Flow<List<Long>> {
        return bodyDao.subscribeBodySummaries().flowOn(dispatcher).map { it.map { it.id } }
    }

    override suspend fun deleteBodyParam(id: Long) = withContext(dispatcher) {
        bodyDao.updateBodyParamDeletedState(id, System.currentTimeMillis())
    }

    override suspend fun restoreBodyParam(id: Long) = withContext(dispatcher) {
        bodyDao.updateBodyParamDeletedState(id, -1L)
    }

    override suspend fun getBodySummary(id: Long) = withContext(dispatcher) {
        bodyDao.getBodySummary(id).mapToDomain()
    }

    override suspend fun getAllSummary(): List<DaySummary> {
        return dayDao.getDaySummaries().map { it.mapToDomain() }.toSet().toList()
    }

    suspend fun <T> run(block: suspend () -> T): T {
        return withContext(dispatcher) {
            block()
        }
    }
}