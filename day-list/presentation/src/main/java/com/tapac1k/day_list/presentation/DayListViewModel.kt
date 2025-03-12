package com.tapac1k.day_list.presentation

import androidx.lifecycle.ViewModel
import com.tapac1k.day.contract.GetCurrentDayIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayListViewModel @Inject constructor(
    private val getCurrentDayIdUseCase: GetCurrentDayIdUseCase
) : ViewModel() {

    fun getCurrentDay() = getCurrentDayIdUseCase.invoke()
}