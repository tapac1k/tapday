package com.tapac1k.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.main.contract_ui.MainRouter
import com.tapac1k.settings.contract_ui.SettingsRouter
import com.tapac1k.training.contract_ui.TrainingRouter
import dagger.Lazy
import javax.inject.Inject

class MainRouterImpl @Inject constructor(
    private val settingsScreenRouter: Lazy<SettingsRouter>,
    private val dayRouter: Lazy<DayRouter>,
    private val trainingRouter: Lazy<TrainingRouter>?,
): MainRouter {

    @Composable
    override fun NavigateToMain(onLoggedOut: () -> Unit) {
        val viewModel = hiltViewModel<MainViewModel>()
        MainScreen(
            viewModel,
            settingsScreenRouter,
            dayRouter,
            trainingRouter,
            onLoggedOut,
        )
    }
}