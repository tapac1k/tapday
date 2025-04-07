package com.tapac1k.day.presentation.habit_dialog

sealed interface HabitDialogEvent {
    data object Dismiss : HabitDialogEvent
    data class ShowMessage(val message: String) : HabitDialogEvent
}