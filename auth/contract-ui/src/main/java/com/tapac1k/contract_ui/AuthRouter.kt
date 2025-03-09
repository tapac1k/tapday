package com.tapac1k.contract_ui

import androidx.compose.runtime.Composable

interface AuthRouter {

    @Composable
    fun NavigateToLogin(onLoggedIn: () -> Unit): Unit
}