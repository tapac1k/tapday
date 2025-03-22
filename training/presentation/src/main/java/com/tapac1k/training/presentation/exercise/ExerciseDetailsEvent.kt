package com.tapac1k.training.presentation.exercise

sealed interface ExerciseDetailsEvent {
    data object Finish : ExerciseDetailsEvent
    data class ShowMessage(val message: String) : ExerciseDetailsEvent
}