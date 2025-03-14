package com.tapac1k.day.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tapac1k.day.contract.Day
import com.tapac1k.day.domain.GetDayUseCase
import com.tapac1k.day.domain.SaveDayUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val saveDayUseCase: SaveDayUseCase,
    private val getDayUseCase: GetDayUseCase,
) : ViewModel() {
    private var updated = false
    private val day = stateHandle.toRoute<Day>()
    private val _state = MutableStateFlow(DayState())
    private val _updates = MutableSharedFlow<StateUpdate>()
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            _updates.collectLatest {
                processStateUpdate(it)
            }
        }
        viewModelScope.launch(Dispatchers.IO) {
            getDayUseCase.invoke(day.day).onSuccess { result ->
                _state.update { it.copy(dayActivity = result.dayActivity) }
            }
        }
    }

    fun updateState(update: StateUpdate) = viewModelScope.launch {
        updated = true
        _updates.emit(update)
    }

    private fun processStateUpdate(stateUpdate: StateUpdate) {
        when (stateUpdate)  {
            is StateUpdate.UpdateMood -> {
                _state.update {
                    it.copy(dayActivity = it.dayActivity.copy(mood = stateUpdate.mood))
                }
            }
            is StateUpdate.UpdateState -> {
                _state.update {
                    it.copy(dayActivity = it.dayActivity.copy(state = stateUpdate.state))
                }
            }
            is StateUpdate.UpdateSleepHours -> {
                _state.update {
                    it.copy(dayActivity = it.dayActivity.copy(sleepHours = stateUpdate.sleepHours))
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