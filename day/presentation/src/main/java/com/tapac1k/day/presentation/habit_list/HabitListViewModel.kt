package com.tapac1k.day.presentation.habit_list

import androidx.lifecycle.SavedStateHandle
import com.tapac1k.compose.ViewModelWithUpdater
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HabitListViewModel @Inject constructor(
    stateHandle: SavedStateHandle,
) : ViewModelWithUpdater<HabitListState, HabitListEvent, HabitListUpdater>(
    HabitListState()
) {

    override fun processUpdate(updater: HabitListUpdater) {
        TODO("Not yet implemented")
    }
}