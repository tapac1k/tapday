package com.tapac1k.day_list.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.day_list.contract_ui.DayListRouter
import javax.inject.Inject

class DayListRouterImpl @Inject constructor(): DayListRouter {
    @Composable
    override fun NavigateDayList(onSettings: () -> Unit, openDay: () -> Unit) {
        val viewModel = hiltViewModel<DayListViewModel>()
        DayListScreen(viewModel, onSettings)
    }
}