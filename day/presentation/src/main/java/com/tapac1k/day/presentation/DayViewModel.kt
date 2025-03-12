package com.tapac1k.day.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.tapac1k.day.contract.Day
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    stateHandle: SavedStateHandle
) : ViewModel() {
    private val day = stateHandle.toRoute<Day>()

    init {
        println("TestX: $day $this")
    }

    override fun onCleared() {
        super.onCleared()
        println("TestX: onCleared $this")
    }
}