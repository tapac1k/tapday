package com.tapac1k.day.presentation.daylist

sealed class DayListEvent {
    data object OpenSettings : DayListEvent()
    data class OpenDayDetails(val dayId: Long) : DayListEvent()
}