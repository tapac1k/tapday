package com.tapac1k.day.di

import com.tapac1k.day.presentation.DaySettingsProvider
import com.tapac1k.settings.contract_ui.SettingProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface DaySettingsProviderModule {
    @Binds
    @IntoSet
    fun bindDaySettingsProvider(
        daySettingsProvider: DaySettingsProvider
    ): SettingProvider
}