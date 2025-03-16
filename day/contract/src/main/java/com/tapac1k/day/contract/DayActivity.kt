package com.tapac1k.day.contract


data class DayActivity(
    val sleepHours: Float = 0f,
    val mood: Int = 0,
    val state: Int = 0,
)

fun DayActivity.getFormatSleepHours(): String {
    val hours = sleepHours.toInt()
    val minutes = ((sleepHours - hours) * 60).toInt()
    return "$hours:$minutes"
}

