package com.tapac1k.training.di

import com.tapac1k.main.contract_ui.SettingProvider
import com.tapac1k.training.presentation.TrainingSettingsProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
interface TrainingSettingsModule {
    @Binds
    @IntoSet
    fun bindTrainingSettings(impl: TrainingSettingsProvider): SettingProvider
}