package com.tapac1k.day.presentation.habit_dialog

import androidx.compose.ui.text.input.TextFieldValue

data class HabitDialogState(
    val name: TextFieldValue = TextFieldValue(""),
    val isPositive: Boolean = true,
    val loading: Boolean = false
)

sealed class HabitDialogUpdate {
    data class Name(val name: TextFieldValue) : HabitDialogUpdate()
    data class IsPositive(val isPositive: Boolean) : HabitDialogUpdate()
}