package com.tapac1k.training.presentation.tags

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapac1k.training.domain.usecase.GetTrainingTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingTagsViewModel @Inject constructor(
    private val getTrainingTagsUseCase: GetTrainingTagsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(TrainingTagsState())
    val state = _state.asStateFlow()

    private val _updates = MutableSharedFlow<TrainingTagsStateUpdate>()

    init {
        loadTrainingTags()
        viewModelScope.launch(Dispatchers.Default) {
            _updates.collect { update ->
                processUpdate(update)
            }
        }
    }

    private fun loadTrainingTags() {
        viewModelScope.launch(Dispatchers.IO) {
            getTrainingTagsUseCase.invoke().collectLatest { tags ->
                _state.update {
                    it.copy(
                        tags = tags,
                        loading = false
                    )
                }
            }
        }
    }

    fun updateState(update: TrainingTagsStateUpdate) {
        viewModelScope.launch {
            _updates.emit(update)
        }
    }

    private fun processUpdate(update: TrainingTagsStateUpdate) {
        _state.update {
            when (update) {
                is TrainingTagsStateUpdate.UpdateQuery -> {
                    it.copy(
                        query = update.query
                    )
                }

                is TrainingTagsStateUpdate.ClearQuery ->  it.copy(
                    query = null
                )
            }
        }
    }
}