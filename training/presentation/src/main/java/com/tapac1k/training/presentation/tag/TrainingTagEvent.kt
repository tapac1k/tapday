package com.tapac1k.training.presentation.tag

sealed interface TrainingTagEvent {
    data object Dismiss : TrainingTagEvent
    data class ShowMessage(val message: String) : TrainingTagEvent
}