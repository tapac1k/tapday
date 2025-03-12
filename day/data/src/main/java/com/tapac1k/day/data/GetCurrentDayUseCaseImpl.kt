package com.tapac1k.day.data

import com.tapac1k.day.contract.GetCurrentDayUseCase
import com.tapac1k.day.domain.DayService
import javax.inject.Inject

class GetCurrentDayUseCaseImpl @Inject constructor(
    private val dayService: DayService
) : GetCurrentDayUseCase {
    override fun invoke() = dayService.getCurrentDay()
}