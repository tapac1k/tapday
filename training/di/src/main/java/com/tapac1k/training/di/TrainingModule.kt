package com.tapac1k.training.di

import com.tapac1k.training.data.TrainingServiceImpl
import com.tapac1k.training.data.usecase.CreateTrainingTagUseCaseImpl
import com.tapac1k.training.data.usecase.EditTrainingTagUseCaseImpl
import com.tapac1k.training.data.usecase.GetTrainingTagsUseCaseImpl
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.CreateTrainingTagUseCase
import com.tapac1k.training.domain.usecase.EditTrainingTagUseCase
import com.tapac1k.training.domain.usecase.GetTrainingTagsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface TrainingModule {
    @Binds
    fun bindTrainingService(service: TrainingServiceImpl): TrainingService
}

@Module
@InstallIn(ViewModelComponent::class)
interface TrainingViewModelModule {
    @Binds
    fun bindGetTrainingTagsUseCase(useCase: GetTrainingTagsUseCaseImpl): GetTrainingTagsUseCase

    @Binds
    fun bindCreateTrainingTagUseCase(useCase: CreateTrainingTagUseCaseImpl): CreateTrainingTagUseCase

    @Binds
    fun bindEditTrainingTagUseCase(useCase: EditTrainingTagUseCaseImpl): EditTrainingTagUseCase
}