package com.tapac1k.day.contract

data class DayInfo(
    val id: Long,
    val dayActivity: DayActivity,
    val updated: Long?,
    val description: String,
    val positiveSum: Int = 0,
    val negativeSum: Int = 0,
)

fun DayInfo.getWeek() = DayUtil.getDayOfWeek(id)

fun DayInfo.getFormattedDate() = DayUtil.getFormattedDay(id)