package com.tapac1k.day.presentation.day

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.day.contract_ui.DayRoute
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.models.HabitData
import com.tapac1k.day.domain.usecase.GetDayUseCase
import com.tapac1k.day.domain.usecase.SaveDayUseCase
import com.tapac1k.day.domain.usecase.SubscribeHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DayViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val saveDayUseCase: SaveDayUseCase,
    private val getDayUseCase: GetDayUseCase,
    private val getHabitsUseCase: SubscribeHabitsUseCase,
) : ViewModelWithUpdater<DayState, DayEvent, StateUpdate>(DayState()) {

    private var updated = false
    private val day = stateHandle.toRoute<DayRoute>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getHabitsUseCase.invoke().first().let {  habits ->
                _state.update {
                    it.copy(
                        habitsData = habits.associate { habit ->
                            habit to 0
                        },
                        loading = false,
                    )
                }
            }
            getDayUseCase.invoke(day.day).onSuccess { result ->
                _state.update {
                    it.copy(
                        dayActivity = result.dayActivity,
                        description = result.description,
                        loading = false,
                        habitsData = it.habitsData.toMutableMap().apply {
                            result.habitsData.forEach { habitData ->
                                this[habitData.habit] = habitData.state
                            }
                        }
                    )
                }
            }
        }
    }

    override fun processUpdate(updater: StateUpdate) {
        if (state.value.loading) return
        updated = true
        when (updater) {
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

            is StateUpdate.UpdateDescription -> {
                _state.update {
                    it.copy(description = updater.desc)
                }
            }

            is StateUpdate.ToggleHabitState -> toggleHabitState(updater.habit)
        }
    }

    private fun toggleHabitState(
        habit: Habit,
    ) {
        _state.update {
            val currentValue = it.habitsData[habit] ?: 0
            val newValue = if (currentValue == 3) 0 else currentValue + 1
            it.copy(
                habitsData = it.habitsData.toMutableMap().apply {
                    this[habit] = newValue
                }
            )
        }
    }

    fun saveDay() {
        if (updated) {
            GlobalScope.launch {
                _state.value.let {
                    saveDayUseCase.invoke(
                        day = day.day,
                        dayActivity = it.dayActivity,
                        description = it.description,
                        habitsData = it.habitsData.entries.map {
                            HabitData(
                                habit = it.key,
                                state = it.value
                            )
                        }.filter { it.state > 0 },
                    )
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveDay()
    }
}