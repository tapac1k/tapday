package com.tapac1k.training.presentation.exercise_history

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.contract_ui.ExerciseHistoryRoute
import com.tapac1k.training.domain.usecase.GetExerciseDetailsUseCase
import com.tapac1k.training.domain.usecase.GetExerciseHistoryUseCase
import com.tapac1k.training.presentation.training_list.TrainingListPagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ExerciseHistoryViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getExerciseHistoryUseCase: GetExerciseHistoryUseCase,
    private val getExerciseDetailsUseCase: GetExerciseDetailsUseCase,
) : ViewModelWithUpdater<ExerciseHistoryState, ExerciseHistoryEvent, ExerciseHistoryUpdater>(
    ExerciseHistoryState(exerciseName = "")
) {
    val id = stateHandle.toRoute<ExerciseHistoryRoute>().id

    val pagingData = Pager(
        PagingConfig(pageSize = 20)
    ) {
        ExerciseHistoryPagingSource(id, getExerciseHistoryUseCase)
    }.flow
        .cachedIn(viewModelScope)

    init {
        launch {
            getExerciseDetailsUseCase.invoke(id).onSuccess { result ->
                _state.update {
                    it.copy(exerciseName = result.name, loading = false)
                }
            }
        }
    }

    override fun processUpdate(updater: ExerciseHistoryUpdater) {
        TODO("Not yet implemented")
    }
}