package com.tapac1k.training.presentation.training_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.domain.usecase.GetTrainingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TrainingListViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getTrainingListUseCase: GetTrainingListUseCase,
) : ViewModelWithUpdater<TrainingListState, TrainingListEvent, TrainingListUpdater>(
    TrainingListState()
) {
    val trainingsFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        TrainingListPagingSource(getTrainingListUseCase)
    }.flow
        .cachedIn(viewModelScope)

    override fun processUpdate(updater: TrainingListUpdater) {

    }
}