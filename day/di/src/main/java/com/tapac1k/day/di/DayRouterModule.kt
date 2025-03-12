package com.tapac1k.day.di

import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.day.presentation.DayRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface DayRouterModule {
    @Binds
    fun bindDayRouter(impl: DayRouterImpl): DayRouter
}