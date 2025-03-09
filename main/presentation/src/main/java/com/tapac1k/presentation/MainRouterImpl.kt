package com.tapac1k.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.main.contract_ui.MainRouter
import javax.inject.Inject

class MainRouterImpl @Inject constructor(): MainRouter {

    @Composable
    override fun NavigateToMain(onLoggedOut: () -> Unit) {
        val viewModel = hiltViewModel<MainViewModel>()
        MainScreen(viewModel, onLoggedOut)
    }
}