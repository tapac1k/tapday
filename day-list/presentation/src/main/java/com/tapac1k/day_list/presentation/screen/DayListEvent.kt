package com.tapac1k.day_list.presentation.screen

sealed class DayListEvent {
    data object OpenSettings : DayListEvent()
    data class OpenDayDetails(val dayId: Long) : DayListEvent()
}