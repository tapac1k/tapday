package com.tapac1k.day.presentation.habit_list

import androidx.compose.ui.text.input.TextFieldValue

sealed interface HabitListUpdater {
    data object ClearQuery : HabitListUpdater
    data class UpdateQuery(val query: TextFieldValue) : HabitListUpdater
}
