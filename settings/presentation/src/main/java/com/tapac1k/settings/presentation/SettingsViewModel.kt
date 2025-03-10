package com.tapac1k.settings.presentation

import androidx.lifecycle.ViewModel
import com.tapac1k.auth.contract.LogoutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    val logoutUseCase: LogoutUseCase
): ViewModel() {
    fun logout() {
        logoutUseCase.invoke()
    }
}