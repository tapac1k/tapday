package com.tapac1k.training.presentation.exercise_history

import com.tapac1k.training.contract.ExerciseGroup

data class ExerciseHistoryState(
    val loading: Boolean = true,
    val exerciseName: String,
)
