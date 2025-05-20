package com.tapac1k.day.domain.usecase

import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.domain.models.HabitData

interface SaveDayUseCase {
    suspend fun invoke(
        day: Long,
        dayActivity: DayActivity,
        description: String,
        habitsData: List<HabitData>,
    ): Result<Unit>
}