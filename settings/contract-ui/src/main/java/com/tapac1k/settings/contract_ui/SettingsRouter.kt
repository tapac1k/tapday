package com.tapac1k.settings.contract_ui

import androidx.compose.runtime.Composable

interface SettingsRouter {
    @Composable
    fun NavigateToSettings(settingsNavigation: SettingsNavigation)
}