package com.tapac1k.day_list.presentation

import androidx.lifecycle.ViewModel
import com.tapac1k.day.contract.GetCurrentDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayListViewModel @Inject constructor(
    private val getCurrentDayUseCase: GetCurrentDayUseCase
) : ViewModel() {

    fun getCurrentDay() = getCurrentDayUseCase.invoke()
}