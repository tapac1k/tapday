package com.tapac1k.day.presentation.habit_list

import androidx.lifecycle.SavedStateHandle
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.day.domain.usecase.SubscribeHabitsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
    private val subscribeHabitsUseCase: SubscribeHabitsUseCase,
) : ViewModelWithUpdater<HabitListState, HabitListEvent, HabitListUpdater>(
    HabitListState(emptyList())
) {
    init {
        launch {
            subscribeHabitsUseCase.invoke().collect{
                updateState {
                    copy(
                        habits = it,
                        loading = false
                    )
                }
            }
        }

    }

    override fun processUpdate(updater: HabitListUpdater) {
        when (updater) {
            HabitListUpdater.ClearQuery -> {
                updateState {
                    copy(
                        query = null
                    )
                }
            }
            is HabitListUpdater.UpdateQuery -> {
                updateState {
                    copy(
                        query = updater.query
                    )
                }
            }
        }
    }
}