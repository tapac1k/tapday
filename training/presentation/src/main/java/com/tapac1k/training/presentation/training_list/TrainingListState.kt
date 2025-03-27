package com.tapac1k.training.presentation.training_list

import com.tapac1k.training.contract.TrainingTag

data class TrainingListState(
    val loading: Boolean = false,
    val tagFilters: List<TrainingTag> = emptyList(),
    val query: String = "",
)