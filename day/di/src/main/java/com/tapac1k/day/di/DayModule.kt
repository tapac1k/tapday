package com.tapac1k.day.di

import com.tapac1k.day.contract.GetCurrentDayIdUseCase
import com.tapac1k.day.data.DayServiceImpl
import com.tapac1k.day.data.usecase.GetDayListByRangeUseCaseImpl
import com.tapac1k.day.data.usecase.GetCurrentDayIdIdUseCaseImpl
import com.tapac1k.day.data.usecase.GetDayUseCaseImpl
import com.tapac1k.day.data.usecase.SaveDayUseCaseImpl
import com.tapac1k.day.data.usecase.SaveHabitUseCaseImpl
import com.tapac1k.day.data.usecase.SubscribeHabitsUseCaseImpl
import com.tapac1k.day.domain.service.DayService
import com.tapac1k.day.domain.usecase.GetDayListByRangeUseCase
import com.tapac1k.day.domain.usecase.GetDayUseCase
import com.tapac1k.day.domain.usecase.SaveDayUseCase
import com.tapac1k.day.domain.usecase.SaveHabitUseCase
import com.tapac1k.day.domain.usecase.SubscribeHabitsUseCase
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

    @Binds
    fun bindGetDayListByRangeUseCase(impl: GetDayListByRangeUseCaseImpl): GetDayListByRangeUseCase

    @Binds
    fun bindSaveHabitUseCase(impl: SaveHabitUseCaseImpl): SaveHabitUseCase
    
    @Binds
    fun bindSubscribeHabitUseCase(impl: SubscribeHabitsUseCaseImpl): SubscribeHabitsUseCase
}