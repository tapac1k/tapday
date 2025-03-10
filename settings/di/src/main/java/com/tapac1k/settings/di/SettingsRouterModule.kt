package com.tapac1k.settings.di

import com.tapac1k.settings.contract_ui.SettingsRouter
import com.tapac1k.settings.presentation.SettingsRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface SettingsRouterModule {
    @Binds
    fun bindSettingsRouter(impl: SettingsRouterImpl): SettingsRouter
}