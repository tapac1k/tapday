package com.tapac1k.training.presentation

import com.tapac1k.main.contract_ui.SettingProvider
import com.tapac1k.training.contract_ui.ExerciseListRoute
import com.tapac1k.training.contract_ui.TrainingTagsRoute
import javax.inject.Inject

class TrainingSettingsProvider @Inject constructor(

) : SettingProvider {
    override fun title() = "Training"

    override fun priority(): Int = 0

    override fun provideList(): List<SettingProvider.Setting> {
        return listOf(
            SettingProvider.Setting("Exercises", ExerciseListRoute()),
            SettingProvider.Setting("Tags", TrainingTagsRoute),
        )
    }
}