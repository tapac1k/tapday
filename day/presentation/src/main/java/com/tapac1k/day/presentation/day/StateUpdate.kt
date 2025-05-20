package com.tapac1k.day.presentation.day

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.day.domain.models.Habit

sealed interface StateUpdate{

    data class UpdateMood(val mood: Int) : StateUpdate
    data class UpdateSleepHours(val sleepHours: Float) : StateUpdate
    data class UpdateState(val state: Int) : StateUpdate
    data class UpdateDescription(val desc: TextFieldValue) : StateUpdate
    data class ToggleHabitState(val habit: Habit) : StateUpdate
}