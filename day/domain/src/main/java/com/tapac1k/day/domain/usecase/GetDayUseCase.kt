package com.tapac1k.day.domain.usecase

import com.tapac1k.day.contract.DayInfo

interface GetDayUseCase {
    suspend fun invoke(day: Long): Result<DayInfo>
}