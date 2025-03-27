package com.tapac1k.day.presentation.day

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.day.contract_ui.DayRoute
import com.tapac1k.day.domain.usecase.GetDayUseCase
import com.tapac1k.day.domain.usecase.SaveDayUseCase
import com.tapac1k.day.presentation.StateUpdate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val saveDayUseCase: SaveDayUseCase,
    private val getDayUseCase: GetDayUseCase,
) : ViewModelWithUpdater<DayState, DayEvent, StateUpdate>(
    DayState()
)
{
    private var updated = false
    private val day = stateHandle.toRoute<DayRoute>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getDayUseCase.invoke(day.day).onSuccess { result ->
                _state.update { it.copy(dayActivity = result.dayActivity) }
            }
        }
    }

    override fun processUpdate(updater: StateUpdate) {
        updated = true
        when (updater)  {
            is StateUpdate.UpdateMood -> {
                _state.update {
                    it.copy(dayActivity = it.dayActivity.copy(mood = updater.mood))
                }
            }
            is StateUpdate.UpdateState -> {
                _state.update {
                    it.copy(dayActivity = it.dayActivity.copy(state = updater.state))
                }
            }
            is StateUpdate.UpdateSleepHours -> {
                _state.update {
                    it.copy(dayActivity = it.dayActivity.copy(sleepHours = updater.sleepHours))
                }
            }

        }
    }

    fun saveDay() {
        if (updated) {
            GlobalScope.launch {
                saveDayUseCase.invoke(day.day, _state.value.dayActivity)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveDay()
    }
}