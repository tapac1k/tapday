package com.tapac1k.auth.presentation

sealed interface AuthEvent {
    data object LoggedIn : AuthEvent
}