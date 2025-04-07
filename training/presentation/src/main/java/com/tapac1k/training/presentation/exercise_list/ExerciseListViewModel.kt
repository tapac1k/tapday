package com.tapac1k.training.presentation.exercise_list

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.contract_ui.ExerciseListRoute
import com.tapac1k.training.domain.usecase.GetExerciseListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
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
            query = route.query?.let { TextFieldValue(it) },
            tags = emptySet()
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
        when (updater) {
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

            is ExerciseListStateUpdater.AddFilterTag -> {
                _state.update {
                    it.copy(
                        tags = it.tags + updater.tag
                    )
                }
            }

            is ExerciseListStateUpdater.RemoveFilterTag -> {
                _state.update {
                    it.copy(
                        tags = it.tags - updater.tag
                    )
                }
            }
        }
    }
}