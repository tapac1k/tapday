package com.tapac1k.training.presentation.training_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract_ui.TrainingDetailsRoute
import com.tapac1k.training.domain.usecase.SaveTrainingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingDetailsViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val saveTrainingUseCase: SaveTrainingUseCase,
) : ViewModelWithUpdater<TrainingDetailsState, TrainingDetailsEvent, TrainingDetailsUpdater>(
    TrainingDetailsState()
) {
    private val saveRequests = MutableSharedFlow<Unit>()
    var id = stateHandle.toRoute<TrainingDetailsRoute>().id

    var generateId = 0
        get() {
            field++
            return field
        }

    init {
        Log.d("TrainingDetailsViewModel", "init $this")
        viewModelScope.launch {
            saveRequests.collectLatest {
                GlobalScope.launch {
                    saveTrainingUseCase.invoke(id, state.value.exercises).onSuccess {
                        id = it
                    }
                }.join()
            }
        }
    }

    fun saveTraining() {
        viewModelScope.launch {
            saveRequests.emit(Unit)
        }
    }

    override fun processUpdate(updater: TrainingDetailsUpdater) {
        when (updater) {
            is TrainingDetailsUpdater.AddExercise -> {
                _state.update {
                    it.copy(
                        exercises = it.exercises + ExerciseGroup(
                            generateId.toString(),
                            updater.exercise,
                            emptyList(),
                        )
                    )
                }
            }

            is TrainingDetailsUpdater.AddSet -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.SetupSet(
                            exerciseGroupId = updater.exerciseGroup.id,
                            set = null,
                            withWeight = updater.exerciseGroup.exercise.withWeight,
                            timeBased = updater.exerciseGroup.exercise.timeBased
                        )
                    )
                }
            }

            is TrainingDetailsUpdater.EditSet -> {
                _state.update {
                    it.copy(
                        dialogState = DialogState.SetupSet(
                            exerciseGroupId = updater.exerciseGroup.id,
                            set = updater.exerciseSet,
                            withWeight = updater.exerciseGroup.exercise.withWeight,
                            timeBased = updater.exerciseGroup.exercise.timeBased
                        )
                    )
                }
            }

            is TrainingDetailsUpdater.ConfirmRemoveExercise -> TODO()
            is TrainingDetailsUpdater.DeleteSet -> TODO()
            is TrainingDetailsUpdater.DismissDialogs -> {
                _state.update {
                    it.copy(dialogState = null)
                }
            }
            is TrainingDetailsUpdater.RemoveExercise -> TODO()
            is TrainingDetailsUpdater.SetupSetConfirm -> {
                updateSet(updater.exerciseGroupId, updater.setId, updater.weight, updater.time, updater.reps)
            }
        }
    }

    private fun updateSet(exerciseGroupId: String, setId: String?, weight: Float?, time: Int?, reps: Int?) {
        val toRemove = (weight == null && time == null && reps == null)
        _state.update {
            it.copy(
                exercises = it.exercises.map {
                    when {
                        it.id != exerciseGroupId -> it
                        toRemove -> it.copy(sets = it.sets.filter { it.id != setId })
                        setId != null -> {
                            it.copy(
                                sets = it.sets.map {
                                    if (it.id == setId) {
                                        it.copy(
                                            weight = weight ?: it.weight,
                                            time = time ?: it.time,
                                            reps = reps ?: it.reps
                                        )
                                    } else {
                                        it
                                    }
                                }
                            )
                        }

                        else -> it.copy(sets = it.sets + ExerciseSet(generateId.toString(), weight, time, reps))
                    }
                },

                dialogState = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        saveTraining()
    }
}