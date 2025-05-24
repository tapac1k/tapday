package com.tapac1k.day.data.usecase

import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.models.HabitData
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.tapday.data.DatabaseRepositoryImpl
import com.tapac1k.tapday.data.createDatabaseRepository
import com.tapac1k.tapquote.data.db.MainDatabase
import com.tapac1k.utils.common.resultOf
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import java.text.SimpleDateFormat
import java.util.concurrent.Executors
import javax.inject.Inject

class SyncDaysUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val dayService: DayService,
) {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    suspend fun invoke(): Result<Unit> = resultOf {
        val dayFormat = SimpleDateFormat("dd/MM/yyyy")
        val repository = createDatabaseRepository(context)
        val dayInfos = repository.getAllSummary()

        val habitsMap = mutableMapOf<Long, Habit>()
        val dayParams = repository.getDayParams().associate {
            it.id to it
        }

        dayParams.forEach {
            val param = it.value
            val habitId = dayService.saveHabit(
                Habit(
                    id = "",
                    name = param.title,
                    isPositive = param.type.isGood(),
                )
            ).onSuccess {
                Log.d("sync", "saved habit ${param.title}")
            }.getOrThrow()
            habitsMap[it.key] = Habit(
                habitId, param.title, param.type.isGood()
            )
        }

        dayInfos.map {  daySummary -> scope.async {
            val dayActivity = daySummary.let {
                DayActivity(
                    sleepHours = it.sleepHours ?: return@let null,
                    mood = it.happiness ?: return@let null,
                    state = it.happiness ?: return@let null,
                )
            }
            val desc = daySummary.description
            val habitData = daySummary.parameters.map {
                val param = dayParams[it.id] ?: return@map null
                val stepped = param.type.isStable()
                val habit = habitsMap.get(it.id) ?: run {

                    Log.d("sync", "no habit found")
                    return@map null
                }
                HabitData(
                    habit = habit,
                    state = if (stepped) it.value * 2 - 1 else it.value * 2,
                )
            }
            dayService.saveDayActivity(
                day = daySummary.id,
                dayActivity = dayActivity ?: return@async,
                description = desc,
                habitData = habitData.filterNotNull(),
            ).onSuccess {
                val date = dayFormat.format(daySummary.id * 1000 * 60 * 60 * 24 )
                Log.d("sync", "saved day ${date} ${daySummary.id}")
            }.getOrThrow()
        }
        }.awaitAll()
        Unit
    }.onSuccess {
        Log.d("sync", "finished")
    }.onFailure {
        Log.e("sync", "failed", it)
    }
}