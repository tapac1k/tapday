package com.tapac1k.day.domain

import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo

interface DayService {
    fun getCurrentDay(): Long
    suspend fun saveDayActivity(day: Long, dayActivity: DayActivity): Result<Unit>
    suspend fun getDayInfo(day: Long): Result<DayInfo>
}