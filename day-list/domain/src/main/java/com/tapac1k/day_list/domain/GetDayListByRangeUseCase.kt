package com.tapac1k.day_list.domain

import com.tapac1k.day.contract.DayInfo

interface GetDayListByRangeUseCase {
    suspend fun invoke(from: Long, to: Long): Result<List<DayInfo>>
}