package com.tapac1k.day.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.contract_ui.DayRouter
import javax.inject.Inject

class DayRouterImpl @Inject constructor() : DayRouter {

    @Composable
    override fun NavigateDayScreen(dayNavigation: DayNavigation) {
        val viewModel = hiltViewModel<DayViewModel>()
        DayScreen(viewModel, dayNavigation)
    }
}