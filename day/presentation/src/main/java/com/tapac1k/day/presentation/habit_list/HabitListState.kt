package com.tapac1k.day.presentation.habit_list

import android.app.DownloadManager.Query
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.day.domain.models.Habit

data class HabitListState(
    val habits: List<Habit>,
    val query: TextFieldValue? = null,
    val loading: Boolean = true,
) {
    private val filteredHabits = habits.filter { habit ->
        query?.text?.takeIf { it.isNotBlank() }?.let {
            habit.name.contains(it, true)
        } ?: true
    }
    val positiveHabits = filteredHabits.filter { it.isPositive }
    val negativeHabits = filteredHabits.filter { !it.isPositive }
}
