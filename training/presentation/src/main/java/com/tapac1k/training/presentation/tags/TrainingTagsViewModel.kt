package com.tapac1k.training.presentation.tags

import androidx.lifecycle.viewModelScope
import com.tapac1k.compose.ViewModelWithUpdater
import com.tapac1k.training.domain.usecase.GetTrainingTagsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrainingTagsViewModel @Inject constructor(
    private val getTrainingTagsUseCase: GetTrainingTagsUseCase
) : ViewModelWithUpdater<TrainingTagsState, Unit, TrainingTagsStateUpdate>(TrainingTagsState()) {

    init {
        loadTrainingTags()
    }

    private fun loadTrainingTags() {
        viewModelScope.launch(Dispatchers.IO) {
            getTrainingTagsUseCase.invoke().collectLatest { tags ->
                _state.update {
                    it.copy(
                        tags = tags,
                        loading = false
                    )
                }
            }
        }
    }

    override fun processUpdate(updater: TrainingTagsStateUpdate) {
        _state.update {
            when (updater) {
                is TrainingTagsStateUpdate.UpdateQuery -> {
                    it.copy(
                        query = updater.query
                    )
                }

                is TrainingTagsStateUpdate.ClearQuery ->  it.copy(
                    query = null
                )
            }
        }
    }
}