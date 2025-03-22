package com.tapac1k.training.presentation.training_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.tapac1k.compose.ViewModelWithUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
) : ViewModelWithUpdater<TrainingListState, TrainingListEvent, TrainingListUpdater>(
    TrainingListState()
) {

    override fun processUpdate(updater: TrainingListUpdater) {
        TODO("Not yet implemented")
    }
}