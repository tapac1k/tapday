package com.tapac1k.training.presentation.exercise_history

sealed interface ExerciseHistoryEvent {
    data object Finish : ExerciseHistoryEvent
    data class ShowMessage(val message: String) : ExerciseHistoryEvent
}
