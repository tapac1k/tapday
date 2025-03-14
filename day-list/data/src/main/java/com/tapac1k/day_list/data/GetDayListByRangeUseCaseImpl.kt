package com.tapac1k.day_list.data

import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day_list.domain.DayListService
import com.tapac1k.day_list.domain.GetDayListByRangeUseCase
import javax.inject.Inject

class GetDayListByRangeUseCaseImpl @Inject constructor(
    private val service: DayListService
) : GetDayListByRangeUseCase {
    override suspend fun invoke(from: Long, to: Long): Result<List<DayInfo>> {
        return service.requestDayList(from, to)
    }
}