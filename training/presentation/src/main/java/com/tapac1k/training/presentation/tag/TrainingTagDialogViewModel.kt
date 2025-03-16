package com.tapac1k.training.presentation.tag

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.contract_ui.TrainingTagRoute
import com.tapac1k.training.domain.usecase.CreateTrainingTagUseCase
import com.tapac1k.training.domain.usecase.EditTrainingTagUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingTagDialogViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val createTrainingTagUseCase: CreateTrainingTagUseCase,
    private val editTrainingTagUseCase: EditTrainingTagUseCase,
) : ViewModel() {
    private val tagId = stateHandle.toRoute<TrainingTagRoute>().tagId

    private val _events = MutableSharedFlow<TrainingTagEvent>(extraBufferCapacity = 1)
    val events = _events.asSharedFlow()

    private val _state = MutableStateFlow(stateHandle.toRoute<TrainingTagRoute>().value.orEmpty())
    val state = _state.asStateFlow()

    fun updateText(text: String) {
        _state.value = text
    }

    fun saveTag() = viewModelScope.launch(Dispatchers.IO) {
        val tag = state.value
        if (tagId == null) {
            createTrainingTagUseCase.invoke(tag)
        } else {
            editTrainingTagUseCase.invoke(tagId, tag)
        }.onSuccess {
            _events.emit(TrainingTagEvent.ShowMessage("Tag saved"))
            _events.emit(TrainingTagEvent.Dismiss)
        }.onFailure {
            it.message?.let {
                _events.emit(TrainingTagEvent.ShowMessage(it))
            }
        }
    }
}