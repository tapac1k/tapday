package com.tapac1k.training.presentation.exercise_list

import com.tapac1k.training.contract.Exercise

data class ExerciseListState(
    val exercises: List<Exercise> = emptyList(),
    val query: String? = null,
    val tags: List<String>? = null,
)