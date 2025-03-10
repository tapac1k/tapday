package com.tapac1k.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapac1k.auth.domain.usecase.GoogleSignInUseCase
import com.tapac1k.auth.domain.usecase.IsUserSignedInUseCase
import com.tapac1k.login.presentation.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val googleSignInUseCase: GoogleSignInUseCase,
    private val isUserSignedInUseCase: IsUserSignedInUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Splash)
    val state = _state.asStateFlow()


    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isUserSignedInUseCase.invoke()) {
                _events.emit(AuthEvent.LoggedIn)
            } else {
                _state.value = AuthState.LoggedOut
            }
        }
    }

    fun login() = viewModelScope.launch(Dispatchers.IO) {
        _state.value = AuthState.Loading
        googleSignInUseCase.signIn().onSuccess {
            _events.emit(AuthEvent.LoggedIn)
        }.onFailure {
            _state.value = AuthState.LoggedOut
        }
    }
}