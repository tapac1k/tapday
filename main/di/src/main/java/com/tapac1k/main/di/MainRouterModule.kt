package com.tapac1k.main.di

import com.tapac1k.main.contract_ui.MainRouter
import com.tapac1k.presentation.MainRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface MainRouterModule {
    @Binds
    fun provideMainRouter(mainRouterImpl: MainRouterImpl): MainRouter
}