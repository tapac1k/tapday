package com.tapac1k.day.di

import com.tapac1k.day.contract.GetCurrentDayUseCase
import com.tapac1k.day.data.DayServiceImpl
import com.tapac1k.day.data.GetCurrentDayUseCaseImpl
import com.tapac1k.day.domain.DayService
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DayModule {
    @Binds
    fun bindDayService(impl: DayServiceImpl): DayService
}

@Module
@InstallIn(ViewModelComponent::class)
interface DayUseCaseModule {
    @Binds
    fun bindGetCurrentDayUseCase(impl: GetCurrentDayUseCaseImpl): GetCurrentDayUseCase
}