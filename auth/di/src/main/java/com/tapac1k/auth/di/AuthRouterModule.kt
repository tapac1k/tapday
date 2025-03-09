package com.tapac1k.auth.di

import com.tapac1k.auth.presentation.AuthRouterImpl
import com.tapac1k.contract_ui.AuthRouter
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface AuthRouterModule {
    @Binds
    fun bindAuthRouter(authRouterImpl: AuthRouterImpl): AuthRouter
}