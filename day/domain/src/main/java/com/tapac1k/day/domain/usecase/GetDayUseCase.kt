package com.tapac1k.day.domain.usecase

import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.models.FullDayInfo

interface GetDayUseCase {
    suspend fun invoke(day: Long): Result<FullDayInfo>
}