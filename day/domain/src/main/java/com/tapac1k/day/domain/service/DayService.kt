package com.tapac1k.day.domain.service

import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.models.FullDayInfo
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.models.HabitData
import kotlinx.coroutines.flow.Flow

interface DayService {
    fun getCurrentDay(): Long
    suspend fun saveDayActivity(
        day: Long,
        dayActivity: DayActivity,
        description: String,
        habitData: List<HabitData>
    ): Result<Unit>

    suspend fun getDayInfo(day: Long): Result<FullDayInfo>
    suspend fun requestDayList(from: Long, to: Long): Result<List<DayInfo>>

    suspend fun saveHabit(habit: Habit): Result<Unit> // if habit.id == -1L, create new habit
    suspend fun subscribeAllHabits(): Flow<List<Habit>>
}