package com.tapac1k.day_list.di

import com.tapac1k.day_list.contract_ui.DayListRouter
import com.tapac1k.day_list.presentation.DayListRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface DayListRouterModule {
    @Binds
    fun bindDayListRouter(impl: DayListRouterImpl): DayListRouter
}