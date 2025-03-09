package com.tapac1k.main.contract_ui

import androidx.compose.runtime.Composable

interface MainRouter {
    @Composable
    fun NavigateToMain(
        onLoggedOut: () -> Unit,
    ): Unit
}