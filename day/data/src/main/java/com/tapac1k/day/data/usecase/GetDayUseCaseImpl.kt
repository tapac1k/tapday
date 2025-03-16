package com.tapac1k.day.data.usecase

import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.day.domain.usecase.GetDayUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDayUseCaseImpl @Inject constructor(
    private val service: DayService
) : GetDayUseCase {
    override suspend fun invoke(day: Long): Result<DayInfo> = withContext(Dispatchers.IO) {
        service.getDayInfo(day)
    }
}