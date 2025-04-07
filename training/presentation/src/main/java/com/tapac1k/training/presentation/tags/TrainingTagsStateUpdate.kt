package com.tapac1k.training.presentation.tags

import androidx.compose.ui.text.input.TextFieldValue

sealed interface TrainingTagsStateUpdate {
    data class UpdateQuery(val query: TextFieldValue) : TrainingTagsStateUpdate
    data object ClearQuery : TrainingTagsStateUpdate
}