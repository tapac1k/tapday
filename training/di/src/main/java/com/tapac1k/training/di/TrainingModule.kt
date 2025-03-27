package com.tapac1k.training.di

import com.tapac1k.training.contract.SyncDatabaseWithFirebase
import com.tapac1k.training.data.TrainingServiceImpl
import com.tapac1k.training.data.usecase.CreateTrainingTagUseCaseImpl
import com.tapac1k.training.data.usecase.EditTrainingTagUseCaseImpl
import com.tapac1k.training.data.usecase.GetExerciseDetailsUseCaseImpl
import com.tapac1k.training.data.usecase.GetExerciseHistoryUseCaseImpl
import com.tapac1k.training.data.usecase.GetExerciseListUseCaseImpl
import com.tapac1k.training.data.usecase.GetTrainingTagsUseCaseImpl
import com.tapac1k.training.data.usecase.GetTrainingListUseCaseImpl
import com.tapac1k.training.data.usecase.GetTrainingUseCaseImpl
import com.tapac1k.training.data.usecase.SaveExerciseUseCaseImpl
import com.tapac1k.training.data.usecase.SaveTrainingUseCaseImpl
import com.tapac1k.training.data.usecase.SyncDatabaseWithFirebaseImpl
import com.tapac1k.training.domain.TrainingService
import com.tapac1k.training.domain.usecase.CreateTrainingTagUseCase
import com.tapac1k.training.domain.usecase.EditTrainingTagUseCase
import com.tapac1k.training.domain.usecase.GetExerciseDetailsUseCase
import com.tapac1k.training.domain.usecase.GetExerciseHistoryUseCase
import com.tapac1k.training.domain.usecase.GetExerciseListUseCase
import com.tapac1k.training.domain.usecase.GetTrainingTagsUseCase
import com.tapac1k.training.domain.usecase.GetTrainingListUseCase
import com.tapac1k.training.domain.usecase.GetTrainingUseCase
import com.tapac1k.training.domain.usecase.SaveExerciseUseCase
import com.tapac1k.training.domain.usecase.SaveTrainingUseCase
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

    @Binds
    fun bindGetExerciseListUseCase(useCase: GetExerciseListUseCaseImpl): GetExerciseListUseCase

    @Binds
    fun provideGetExerciseDetailsUseCase(impl: GetExerciseDetailsUseCaseImpl): GetExerciseDetailsUseCase

    @Binds
    fun provideSaveExerciseUseCase(impl: SaveExerciseUseCaseImpl): SaveExerciseUseCase

    @Binds
    fun bindSaveTrainingUseCase(impl: SaveTrainingUseCaseImpl): SaveTrainingUseCase

    @Binds
    fun bindSyncDatabaseWithFirebaseUseCase(impl: SyncDatabaseWithFirebaseImpl): SyncDatabaseWithFirebase

    @Binds
    fun bindGetTrainingsUseCase(useCase: GetTrainingListUseCaseImpl): GetTrainingListUseCase

    @Binds
    fun bindGetTrainingUseCase(useCase: GetTrainingUseCaseImpl): GetTrainingUseCase

    @Binds
    fun bindGetExerciseHistoryUseCase(useCase: GetExerciseHistoryUseCaseImpl): GetExerciseHistoryUseCase
}