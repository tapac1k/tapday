package com.tapac1k.training.presentation.training_details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract_ui.TrainingDetailsRoute
import com.tapac1k.training.domain.usecase.GetTrainingUseCase
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
    private val getTrainingUseCase: GetTrainingUseCase,
) : ViewModelWithUpdater<TrainingDetailsState, TrainingDetailsEvent, TrainingDetailsUpdater>(
    TrainingDetailsState()
) {
    private val saveRequests = MutableSharedFlow<Unit>()
    var id = stateHandle.toRoute<TrainingDetailsRoute>().id
    var updated = false
    var generateId = 0L
        get() {
            return System.currentTimeMillis()
        }

    init {
        Log.d("TrainingDetailsViewModel", "init $this")
        viewModelScope.launch {
            saveRequests.collectLatest {
                if (!updated) return@collectLatest
                GlobalScope.launch {
                    saveTrainingUseCase.invoke(id, state.value.exercises, state.value.date, "").onSuccess {
                        id = it
                    }
                }.join()
            }
        }
        viewModelScope.launch {
            id?.let {
                getTrainingUseCase.invoke(it).onSuccess { training ->
                    _state.update {
                        it.copy(
                            exercises = training.exerciseGroup,
                            date = training.date,
                            loading = false,
                        )
                    }
                }.onFailure {
                    it.printStackTrace()
                    _events.emit(TrainingDetailsEvent.ShowToast(it.message ?: "Something went wrong"))
                    _events.emit(TrainingDetailsEvent.Finish)
                }
            } ?: _state.update { it.copy(loading = false) }
        }
    }

    fun saveTraining() {
        viewModelScope.launch {
            saveRequests.emit(Unit)
        }
    }

    override fun processUpdate(updater: TrainingDetailsUpdater) {
        if (state.value.loading) return
        if (updater is TrainingDetailsUpdater.ChangeTraingingUpdater) {
            updated = true
        }
        when (updater) {
            is TrainingDetailsUpdater.AddExercise -> {
                _state.update {
                    it.copy(
                        exercises = it.exercises + ExerciseGroup(
                            id = generateId.toString(),
                            exercise = updater.exercise,
                            date = it.date,
                            sets = emptyList(),
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

            is TrainingDetailsUpdater.DismissDialogs -> {
                _state.update {
                    it.copy(dialogState = null)
                }
            }
            is TrainingDetailsUpdater.SetupSetConfirm -> {
                updateSet(updater.exerciseGroupId, updater.setId, updater.weight, updater.time, updater.reps)
            }

            TrainingDetailsUpdater.ShowDatePicker -> {
                _state.update {
                    it.copy(dialogState = DialogState.DatePicker(it.date))
                }
            }
            TrainingDetailsUpdater.ShowDescriptionDialog -> {
                _state.update {
                    it.copy(dialogState = DialogState.SetDescriptionDialog(it.description))
                }
            }
            is TrainingDetailsUpdater.RemoveExercise -> TODO()
            is TrainingDetailsUpdater.DeleteSet -> TODO()
            is TrainingDetailsUpdater.ConfirmRemoveExercise -> _state.update { state ->
                state.copy(
                    exercises = state.exercises.filter { it.id != updater.exerciseGroupId }
                )
            }
            is TrainingDetailsUpdater.UpdateDate -> {
                _state.update {
                    it.copy(date = updater.date)
                }
            }
            is TrainingDetailsUpdater.UpdateDescription -> {
                _state.update {
                    it.copy(description = updater.description)
                }
            }

            is TrainingDetailsUpdater.ReplaceExercise -> {
                _state.update {
                    it.copy(
                        exercises = it.exercises.map { group ->
                            if (group.id == updater.exerciseGroupId) {
                                group.copy(exercise = updater.nexExercise)
                            } else {
                                group
                            }
                        }
                    )
                }
            }

            is TrainingDetailsUpdater.RequestReplaceExercise -> {
                launch {
                    _events.emit(TrainingDetailsEvent.ReplaceExercise(updater.exercieGroupId))
                }
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