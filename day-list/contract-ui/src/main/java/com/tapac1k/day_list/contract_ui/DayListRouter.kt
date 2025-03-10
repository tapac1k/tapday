package com.tapac1k.day_list.contract_ui

import androidx.compose.runtime.Composable

interface DayListRouter {

    @Composable
    fun NavigateDayList(
        onSettings: ()-> Unit,
        openDay: () -> Unit
    )
}