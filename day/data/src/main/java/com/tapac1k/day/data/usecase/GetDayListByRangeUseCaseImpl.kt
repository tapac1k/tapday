package com.tapac1k.day.data.usecase

import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.day.domain.usecase.GetDayListByRangeUseCase
import javax.inject.Inject

class GetDayListByRangeUseCaseImpl @Inject constructor(
    private val service: DayService
) : GetDayListByRangeUseCase {
    override suspend fun invoke(from: Long, to: Long): Result<List<DayInfo>> {
        return service.requestDayList(from, to)
    }
}