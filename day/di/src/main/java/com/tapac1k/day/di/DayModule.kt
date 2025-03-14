package com.tapac1k.day.di

import com.tapac1k.day.contract.DayUtil
import com.tapac1k.day.contract.GetCurrentDayIdUseCase
import com.tapac1k.day.data.DayServiceImpl
import com.tapac1k.day.data.usecase.GetCurrentDayIdIdUseCaseImpl
import com.tapac1k.day.data.usecase.GetDayUseCaseImpl
import com.tapac1k.day.data.usecase.SaveDayUseCaseImpl
import com.tapac1k.day.domain.DayService
import com.tapac1k.day.domain.GetDayUseCase
import com.tapac1k.day.domain.SaveDayUseCase
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
    fun bindGetCurrentDayUseCase(impl: GetCurrentDayIdIdUseCaseImpl): GetCurrentDayIdUseCase
    @Binds
    fun bindSaveDayUseCase(impl: SaveDayUseCaseImpl): SaveDayUseCase
    @Binds
    fun bindGetDayUseCase(impl: GetDayUseCaseImpl): GetDayUseCase

}