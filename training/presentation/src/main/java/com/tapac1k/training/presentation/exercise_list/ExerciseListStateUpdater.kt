package com.tapac1k.training.presentation.exercise_list

sealed interface ExerciseListStateUpdater {
    data object ClearQuery : ExerciseListStateUpdater
    data class UpdateQuery(val query: String) : ExerciseListStateUpdater
}