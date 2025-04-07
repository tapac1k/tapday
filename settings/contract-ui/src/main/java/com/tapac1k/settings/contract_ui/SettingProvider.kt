package com.tapac1k.settings.contract_ui

interface SettingProvider {
    fun title(): String
    fun priority(): Int
    fun provideList(): List<Setting>

    data class Setting(
        val title: String,
        val route: Any,
    )
}