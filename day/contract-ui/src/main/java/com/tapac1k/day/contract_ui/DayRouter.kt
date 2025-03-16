package com.tapac1k.day.contract_ui

import androidx.compose.runtime.Composable

interface DayRouter {
    @Composable
    fun NavigateDayScreen(dayNavigation: DayNavigation)

    @Composable
    fun NavigateDayList(
        dayListNavigation: DayListNavigation
    )
}