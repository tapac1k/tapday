package com.tapac1k.training.presentation.exercise_list

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.contract_ui.ExerciseListRoute
import com.tapac1k.training.domain.usecase.GetExerciseListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ExerciseListViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val getExerciseListUseCase: GetExerciseListUseCase,
) : ViewModelWithUpdater<ExerciseListState, Unit, ExerciseListStateUpdater>(
    stateHandle.toRoute<ExerciseListRoute>().let { route ->
        ExerciseListState(
            query = route.query,
            tags = route.tags
        )
    }
) {

    init {
        launch {
            getExerciseListUseCase.invoke().collectLatest { exercises ->
                _state.update {
                    it.copy(
                        exercises = exercises
                    )
                }
            }
        }
    }

    override fun processUpdate(updater: ExerciseListStateUpdater) {
        when(updater) {
            is ExerciseListStateUpdater.UpdateQuery -> {
                _state.update {
                    it.copy(
                        query = updater.query
                    )
                }
            }
            is ExerciseListStateUpdater.ClearQuery -> {
                _state.update {
                    it.copy(
                        query = null
                    )
                }
            }
        }
    }
}