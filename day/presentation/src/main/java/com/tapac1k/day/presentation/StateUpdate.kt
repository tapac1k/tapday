package com.tapac1k.day.presentation

sealed interface StateUpdate{

    data class UpdateMood(val mood: Int) : StateUpdate
    data class UpdateSleepHours(val sleepHours: Float) : StateUpdate
    data class UpdateState(val state: Int) : StateUpdate

}