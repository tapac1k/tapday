package com.tapac1k.training.presentation.exercise

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract_ui.ExerciseDetailsRoute
import com.tapac1k.training.domain.usecase.CreateTrainingTagUseCase
import com.tapac1k.training.domain.usecase.GetExerciseDetailsUseCase
import com.tapac1k.training.domain.usecase.GetTrainingTagsUseCase
import com.tapac1k.training.domain.usecase.SaveExerciseUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val createTrainingTagUseCase: CreateTrainingTagUseCase,
    private val getTrainingsTagUseCase: GetTrainingTagsUseCase,
    private val getExerciseDetailsUseCase: GetExerciseDetailsUseCase,
    private val saveExerciseUseCase: SaveExerciseUseCase,
    ) : ViewModelWithUpdater<ExerciseDetailsState, ExerciseDetailsEvent, ExerciseDetailsUpdater>(
    ExerciseDetailsState()
) {
    val id = stateHandle.toRoute<ExerciseDetailsRoute>().exerciseId

    init {
        launch {
            if (id != null) {
                getExerciseDetailsUseCase.invoke(id).onSuccess { result ->
                    _state.update {
                        it.copy(
                            name = TextFieldValue(result.name),
                            selectedTags = result.tags.toSet(),
                            withWeight = result.withWeight,
                            timeBased = result.timeBased
                        )
                    }
                }.onFailure {
                    _events.emit(ExerciseDetailsEvent.ShowMessage("Error during loading exercise details"))
                    _events.emit(ExerciseDetailsEvent.Finish)
                    return@launch
                }
            }
            getTrainingsTagUseCase.invoke().collectLatest { tags ->
                _state.update {
                    it.copy(tags = tags)
                }
            }
        }
    }

    fun saveExercise() {
        launch {
            saveExerciseUseCase.invoke(
                Exercise(
                    id = id ?: "",
                    name = _state.value.name.text,
                    tags = _state.value.selectedTags.toList(),
                    withWeight = _state.value.withWeight,
                    timeBased = _state.value.timeBased
                )
            ).onSuccess {
                _events.emit(ExerciseDetailsEvent.ShowMessage("Saved"))
                _events.emit(ExerciseDetailsEvent.Finish)
            }.onFailure {
                _events.emit(ExerciseDetailsEvent.ShowMessage("Error during saving exercise"))
            }
        }
    }

    override fun processUpdate(updater: ExerciseDetailsUpdater) {
        when (updater) {
            is ExerciseDetailsUpdater.AddTag -> {
                _state.update {
                    it.copy(selectedTags = it.selectedTags + updater.trainingTag)
                }
            }

            is ExerciseDetailsUpdater.CreateAndAddTag -> {
                TODO()
            }

            is ExerciseDetailsUpdater.RemoveTag -> {
                _state.update {
                    it.copy(selectedTags = it.selectedTags - updater.trainingTag)
                }
            }

            is ExerciseDetailsUpdater.SetWithTime -> {
                _state.update {
                    it.copy(timeBased = updater.withTime)
                }
            }

            is ExerciseDetailsUpdater.SetWithWeight -> {
                _state.update {
                    it.copy(withWeight = updater.withWeight)
                }
            }

            is ExerciseDetailsUpdater.UpdateName -> {
                _state.update {
                    it.copy(name = updater.name)
                }
            }

            is ExerciseDetailsUpdater.UpdateTagQuery -> {
                _state.update {
                    it.copy(tagQuery = updater.tagQuery)
                }
            }
        }
    }

}