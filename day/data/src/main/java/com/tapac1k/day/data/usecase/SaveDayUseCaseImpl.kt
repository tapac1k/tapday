package com.tapac1k.day.data.usecase

import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.day.domain.usecase.SaveDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveDayUseCaseImpl @Inject constructor(
    private val service: DayService
) : SaveDayUseCase {
    override suspend fun invoke(
        day: Long,
        dayActivity: DayActivity,
        description: String,
    ): Result<Unit> = withContext(Dispatchers.IO) {
        service.saveDayActivity(day, dayActivity, description)
    }
}