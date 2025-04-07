package com.tapac1k.day.presentation.day

import androidx.compose.ui.text.input.TextFieldValue

sealed interface StateUpdate{

    data class UpdateMood(val mood: Int) : StateUpdate
    data class UpdateSleepHours(val sleepHours: Float) : StateUpdate
    data class UpdateState(val state: Int) : StateUpdate
    data class UpdateDescription(val desc: TextFieldValue) : StateUpdate

}