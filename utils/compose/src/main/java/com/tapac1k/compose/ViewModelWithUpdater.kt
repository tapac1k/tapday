package com.tapac1k.compose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

abstract class ViewModelWithUpdater<State, Event, Updater>(
    private val initialState: State
) : ViewModel() {
    protected val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()

    protected val _events = MutableSharedFlow<Event>()
    val events = _events.asSharedFlow()

    protected val _updaters = MutableSharedFlow<Updater>()

    init {
        launch(Dispatchers.Default) {
            _updaters.collectLatest { processUpdate(it) }
        }
    }

    fun updateState(update: Updater) = viewModelScope.launch(Dispatchers.Default) {
        _updaters.emit(update)
    }

    protected fun launch(
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> Unit
    ) = viewModelScope.launch(context) {
        block()
    }

    protected abstract fun processUpdate(updater: Updater)
}