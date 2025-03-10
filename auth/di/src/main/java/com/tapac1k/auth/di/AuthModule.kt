package com.tapac1k.auth.di

import com.tapac1k.auth.contract.LogoutUseCase
import com.tapac1k.auth.contract.OnLogoutFlowUseCase
import com.tapac1k.auth.data.AuthServiceImpl
import com.tapac1k.auth.data.usecase.GoogleSignInUseCaseImpl
import com.tapac1k.auth.data.usecase.IsUserSignedInUseCaseImpl
import com.tapac1k.auth.data.usecase.LogoutUseCaseImpl
import com.tapac1k.auth.data.usecase.OnLogoutFlowUseCaseImpl
import com.tapac1k.auth.domain.AuthService
import com.tapac1k.auth.domain.usecase.GoogleSignInUseCase
import com.tapac1k.auth.domain.usecase.IsUserSignedInUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface AuthModule {
    @Binds
    fun provideAuthService(impl: AuthServiceImpl): AuthService


}

@Module
@InstallIn(ViewModelComponent::class)
interface UseCaseModule {

    @Binds
    fun provideLogoutUseCase(impl: LogoutUseCaseImpl): LogoutUseCase

    @Binds
    fun provideOnLogoutFlowUseCase(impl: OnLogoutFlowUseCaseImpl): OnLogoutFlowUseCase

    @Binds
    fun provideGoogleSignInUseCase(impl: GoogleSignInUseCaseImpl): GoogleSignInUseCase

    @Binds
    fun provideIsUserSignedInUseCase(impl: IsUserSignedInUseCaseImpl): IsUserSignedInUseCase
}