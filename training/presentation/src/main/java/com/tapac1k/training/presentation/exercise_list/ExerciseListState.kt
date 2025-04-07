package com.tapac1k.training.presentation.exercise_list

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.TrainingTag

data class ExerciseListState(
    val exercises: List<Exercise> = emptyList(),
    val query: TextFieldValue? = null,
    val tags: Set<TrainingTag> = emptySet(),
) {
    val tagList = tags.sortedBy { it.value }
    val filteredExercises: List<Exercise> = exercises.filter { exercise ->
        val containsQuery = query?.text?.takeIf { it.isNotBlank() }?.let {
            exercise.name.contains(it, ignoreCase = true)
        } ?: true
        val containsTags = tags.isEmpty() || tags.all { exercise.tags.contains(it) }
        containsQuery && containsTags
    }
}