package com.tapac1k.day.domain.models

data class HabitData(
    val habit: Habit,
    val state: Int, // 1 - easy, 2 - medium, 3 - hard
)