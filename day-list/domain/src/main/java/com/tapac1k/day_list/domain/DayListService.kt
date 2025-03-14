package com.tapac1k.day_list.domain

import com.tapac1k.day.contract.DayInfo

interface DayListService {
    suspend fun requestDayList(from: Long, to: Long): Result<List<DayInfo>>
}