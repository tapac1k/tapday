package com.tapac1k.settings.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.settings.contract_ui.SettingsRouter
import javax.inject.Inject

class SettingsRouterImpl @Inject constructor(

) : SettingsRouter {

    @Composable
    override fun NavigateToSettings() {
        val viewModel = hiltViewModel<SettingsViewModel>()
        SettingsScreen(viewModel)
    }
}