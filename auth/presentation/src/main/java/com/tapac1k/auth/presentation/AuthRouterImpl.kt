package com.tapac1k.auth.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.contract_ui.AuthRouter
import javax.inject.Inject

class AuthRouterImpl @Inject constructor(): AuthRouter {
    @Composable
    override fun NavigateToLogin(onLoggedIn: () -> Unit): Unit {
        val viewModel = hiltViewModel<AuthViewModel>()
        AuthScreen(viewModel, onLoggedIn)
    }
}