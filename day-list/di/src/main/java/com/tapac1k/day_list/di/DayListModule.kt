package com.tapac1k.day_list.di

import com.tapac1k.day_list.data.DayListServiceImpl
import com.tapac1k.day_list.data.GetDayListByRangeUseCaseImpl
import com.tapac1k.day_list.domain.DayListService
import com.tapac1k.day_list.domain.GetDayListByRangeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DayListModule {
    @Binds
    fun bindDayListService(impl: DayListServiceImpl): DayListService
}

@Module
@InstallIn(ViewModelComponent::class)
interface DayListUseCaseModule {
    @Binds
    fun bindGetCurrentDayUseCase(impl: GetDayListByRangeUseCaseImpl): GetDayListByRangeUseCase
}