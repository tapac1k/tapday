package com.tapac1k.training.presentation.exercise

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.ui.text.input.TextFieldValue
import androidx.core.os.trace
import com.tapac1k.training.contract.TrainingTag

data class ExerciseDetailsState(
    val id: String? = null,
    val name: TextFieldValue = TextFieldValue(""),
    val withWeight: Boolean = true,
    val timeBased: Boolean = false,
    val selectedTags: Set<TrainingTag> = emptySet(),
    val tags: List<TrainingTag> = emptyList(),
    val tagQuery: TextFieldValue = TextFieldValue(""),
    val loading: Boolean = true,
) {
    val filteredTags = tags.filter {
        ( tagQuery.text.isBlank() || it.value.contains(tagQuery.text, ignoreCase = true)) && !selectedTags.contains(it)
    }
}
