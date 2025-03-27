package com.tapac1k.training.presentation.training_details

sealed interface TrainingDetailsEvent {
    data class ShowToast(val message: String) : TrainingDetailsEvent
    data object Finish : TrainingDetailsEvent
}
