package com.tapac1k.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapac1k.auth.contract.LogoutUseCase
import com.tapac1k.auth.contract.OnLogoutFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val onLogoutFlowUseCase: OnLogoutFlowUseCase,
    private val logoutUseCase: LogoutUseCase,
): ViewModel() {
    private val _events = MutableSharedFlow<MainEvent>()
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            onLogoutFlowUseCase.invoke().collect {
                _events.emit(MainEvent.LoggedOut)
            }
        }
    }
}