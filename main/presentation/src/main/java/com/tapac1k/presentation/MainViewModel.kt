package com.tapac1k.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(): ViewModel() {
    private val _events = MutableSharedFlow<MainEvent>()
    val events = _events.asSharedFlow()

    init {
        Log.d("TestX", "init MainViewModel $this")
    }

    fun logout() = viewModelScope.launch{
       delay(100)
        _events.emit(MainEvent.LoggedOut)
    }
}