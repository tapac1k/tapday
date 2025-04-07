package com.tapac1k.day.contract_ui

import kotlinx.serialization.Serializable

@Serializable
data class DayRoute(val day: Long)

@Serializable
data object DayListRoute


@Serializable
data class DayHabitsRoute(val day: Long)

@Serializable
data object HabitListRoute

@Serializable
data class EditHabitRoute(
    val habitId: String? = null,
    val habitName: String = "",
    val isPositive: Boolean = true,
)

@Serializable
data object EdibleListRoute