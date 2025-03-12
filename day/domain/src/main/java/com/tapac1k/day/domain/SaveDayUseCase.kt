package com.tapac1k.day.domain

import com.tapac1k.day.contract.DayActivity

interface SaveDayUseCase {
    suspend fun invoke(day: Long, dayActivity: DayActivity): Result<Unit>
}