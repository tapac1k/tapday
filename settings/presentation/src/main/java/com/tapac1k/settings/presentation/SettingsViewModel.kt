package com.tapac1k.settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapac1k.auth.contract.LogoutUseCase
import com.tapac1k.settings.contract_ui.SettingProvider
import com.tapac1k.training.contract.SyncDatabaseWithFirebase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingProviders: Set<@JvmSuppressWildcards SettingProvider>,
    private val logoutUseCase: LogoutUseCase,
    private val syncDatabaseWithFirebase: SyncDatabaseWithFirebase,
) : ViewModel() {
    private val _state = MutableStateFlow(SettingsState(settingProviders.sortedByDescending { it.priority() }))
    val state = _state.asStateFlow()

    fun startSync() {
        viewModelScope.launch {
            syncDatabaseWithFirebase.invoke()
        }
    }

    fun logout() {
        logoutUseCase.invoke()
    }
}