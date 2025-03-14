package com.tapac1k.day_list.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.day_list.contract_ui.DayListNavigation
import com.tapac1k.day_list.contract_ui.DayListRouter
import com.tapac1k.day_list.presentation.screen.DayListScreen
import com.tapac1k.day_list.presentation.screen.DayListViewModel
import javax.inject.Inject

class DayListRouterImpl @Inject constructor(): DayListRouter {
    @Composable
    override fun NavigateDayList(dayListNavigation: DayListNavigation) {
        val viewModel = hiltViewModel<DayListViewModel>()
        DayListScreen(viewModel, dayListNavigation)
    }
}