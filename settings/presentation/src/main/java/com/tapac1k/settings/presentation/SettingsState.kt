package com.tapac1k.settings.presentation

import com.tapac1k.settings.contract_ui.SettingProvider

data class SettingsState(
    val settingProviders: Collection<SettingProvider> = emptySet(),
)