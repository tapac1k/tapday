package com.tapac1k.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.day_list.contract_ui.DayListRouter
import com.tapac1k.main.contract_ui.MainRouter
import com.tapac1k.settings.contract_ui.SettingsRouter
import dagger.Lazy
import javax.inject.Inject

class MainRouterImpl @Inject constructor(
    private val dayListRouter: Lazy<DayListRouter>,
    private val settingsScreenRouter: Lazy<SettingsRouter>,
    private val dayRouter: Lazy<DayRouter>
): MainRouter {

    @Composable
    override fun NavigateToMain(onLoggedOut: () -> Unit) {
        val viewModel = hiltViewModel<MainViewModel>()
        MainScreen(
            viewModel,
            dayListRouter,
            settingsScreenRouter,
            dayRouter,
            onLoggedOut
        )
    }
}