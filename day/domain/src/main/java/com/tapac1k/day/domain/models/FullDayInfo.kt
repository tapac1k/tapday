package com.tapac1k.day.domain.models

import com.tapac1k.day.contract.DayActivity

data class FullDayInfo(
    val id: Long,
    val dayActivity: DayActivity,
    val updated: Long?,
    val description: String,
    val positiveSum: Int,
    val negativeSum: Int,
    val habitsData: List<HabitData>,
)