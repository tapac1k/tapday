package com.tapac1k.day.data.usecase

import com.tapac1k.day.contract.GetCurrentDayIdUseCase
import com.tapac1k.day.domain.DayService
import javax.inject.Inject

class GetCurrentDayIdIdUseCaseImpl @Inject constructor(
    private val dayService: DayService
) : GetCurrentDayIdUseCase {
    override fun invoke() = dayService.getCurrentDay()
}