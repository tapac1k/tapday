package com.tapac1k.day.presentation.habit_dialog

import com.tapac1k.day.domain.models.Habit
import com.tapac1k.utils.common.WithBackNavigation

interface HabitListNavigation : WithBackNavigation {
    fun creteHabit()
    fun editHabit(habit: Habit)
}