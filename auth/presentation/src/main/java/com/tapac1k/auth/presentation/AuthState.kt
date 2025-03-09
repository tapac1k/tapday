package com.tapac1k.login.presentation

sealed class AuthState {
    data object Loading: AuthState()
    data object LoggedOut: AuthState()
    data object Error: AuthState()
}