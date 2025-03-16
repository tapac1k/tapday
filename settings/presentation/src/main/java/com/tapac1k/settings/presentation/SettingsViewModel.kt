package com.tapac1k.settings.presentation

import androidx.lifecycle.ViewModel
import com.tapac1k.auth.contract.LogoutUseCase
import com.tapac1k.main.contract_ui.SettingProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingProviders: Set<@JvmSuppressWildcards SettingProvider>,
    private val logoutUseCase: LogoutUseCase,
): ViewModel() {
    private val _state = MutableStateFlow(SettingsState(settingProviders.sortedByDescending { it.priority() }))
    val state = _state.asStateFlow()

    fun logout() {
        logoutUseCase.invoke()
    }
}