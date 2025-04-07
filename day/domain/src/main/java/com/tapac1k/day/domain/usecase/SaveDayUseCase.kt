package com.tapac1k.day.domain.usecase

import com.tapac1k.day.contract.DayActivity

interface SaveDayUseCase {
    suspend fun invoke(
        day: Long,
        dayActivity: DayActivity,
        description: String,
    ): Result<Unit>
}