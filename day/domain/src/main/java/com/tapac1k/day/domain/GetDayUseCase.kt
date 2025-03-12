package com.tapac1k.day.domain

import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo

interface GetDayUseCase {
    suspend fun invoke(day: Long): Result<DayInfo>
}