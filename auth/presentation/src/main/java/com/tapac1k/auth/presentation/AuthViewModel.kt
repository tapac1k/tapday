package com.tapac1k.auth.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapac1k.login.presentation.AuthState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state = _state.asStateFlow()


    private val _events = MutableSharedFlow<AuthEvent>()
    val events = _events.asSharedFlow()

    init {
        Log.d("TestX", "init AuthViewModel $this")
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            // Replace with actual login status check
            delay(1000)
            _state.value = AuthState.LoggedOut
        }
    }

    fun login() {
        _state.value = AuthState.Loading
        viewModelScope.launch {
            // Replace with actual login status check
            delay(1000)
            _events.emit(AuthEvent.LoggedIn)
        }
    }
}