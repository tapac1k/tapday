package com.tapac1k.training.di

import com.tapac1k.training.contract_ui.TrainingRouter
import com.tapac1k.training.presentation.TrainingRouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TrainingRouterModule {
    @Binds
    fun bindTrainingRouter(impl: TrainingRouterImpl): TrainingRouter
}