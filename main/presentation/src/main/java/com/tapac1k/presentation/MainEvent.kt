package com.tapac1k.presentation

sealed class MainEvent {
    data object LoggedOut: MainEvent()
}