package com.tapac1k.day.presentation.day

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.models.HabitData

data class DayState(
    val dayActivity: DayActivity = DayActivity(),
    val description: TextFieldValue = TextFieldValue(""),
    val habitsData: Map<Habit, Int> = emptyMap(),
    val loading: Boolean = true,
) {
    val habits = habitsData.entries.map { (HabitData(it.key, it.value)) }

    val positive = habits.filter { it.habit.isPositive }
    val negative = habits.filter { !it.habit.isPositive }
}
