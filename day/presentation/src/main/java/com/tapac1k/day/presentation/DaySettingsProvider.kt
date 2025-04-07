package com.tapac1k.day.presentation

import com.tapac1k.day.contract_ui.EdibleListRoute
import com.tapac1k.day.contract_ui.HabitListRoute
import com.tapac1k.settings.contract_ui.SettingProvider
import javax.inject.Inject

class DaySettingsProvider @Inject constructor(

) : SettingProvider {
    override fun title() = "Diary"

    override fun priority(): Int = 0

    override fun provideList(): List<SettingProvider.Setting> {
        return listOf(
            SettingProvider.Setting("Habits", HabitListRoute),
            SettingProvider.Setting("Edibles", EdibleListRoute),
        )
    }
}