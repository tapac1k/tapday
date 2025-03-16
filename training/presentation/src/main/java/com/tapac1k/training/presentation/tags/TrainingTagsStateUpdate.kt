package com.tapac1k.training.presentation.tags

sealed interface TrainingTagsStateUpdate {
    data class UpdateQuery(val query: String) : TrainingTagsStateUpdate
    data object ClearQuery : TrainingTagsStateUpdate
}