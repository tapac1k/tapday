package com.tapac1k.day.presentation.habit_dialog

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.day.contract_ui.EditHabitRoute
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.domain.usecase.SaveHabitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HabitDialogViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val saveHabitUseCase: SaveHabitUseCase,
) : ViewModelWithUpdater<HabitDialogState, HabitDialogEvent, HabitDialogUpdate>(stateHandle.toRoute<EditHabitRoute>().asState()) {
    private val route = stateHandle.toRoute<EditHabitRoute>()

    override fun processUpdate(updater: HabitDialogUpdate) {
        if (_state.value.loading) return
        when (updater) {
            is HabitDialogUpdate.Name -> {
                _state.update {
                    it.copy(
                        name = updater.name,
                    )
                }
            }

            is HabitDialogUpdate.IsPositive -> {
                _state.update {
                    it.copy(
                        isPositive = updater.isPositive,
                    )
                }
            }
        }
    }

    fun saveHabit() {
        val habitToSave = _state.value.let {
            Habit(
                id = route.habitId.orEmpty(),
                name = it.name.text,
                isPositive = it.isPositive,
            )
        }
        launch {
            _state.update {
                it.copy(
                    loading = true,
                )
            }
            saveHabitUseCase.invoke(habitToSave).onSuccess {
                _events.emit(HabitDialogEvent.ShowMessage("Saved"))
                _events.emit(HabitDialogEvent.Dismiss)
            }.onFailure {
                _events.emit(HabitDialogEvent.ShowMessage(it.message.orEmpty()))
                _state.update {
                    it.copy(
                        loading = false,
                    )
                }
            }
        }
    }
}

private fun EditHabitRoute.asState() = HabitDialogState(
    name = TextFieldValue(habitName),
    isPositive = isPositive,
    loading = false
)