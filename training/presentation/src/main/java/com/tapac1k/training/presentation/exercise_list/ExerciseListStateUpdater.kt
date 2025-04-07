package com.tapac1k.training.presentation.exercise_list

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.training.contract.TrainingTag

sealed interface ExerciseListStateUpdater {
    data object ClearQuery : ExerciseListStateUpdater
    data class UpdateQuery(val query: TextFieldValue) : ExerciseListStateUpdater
    data class AddFilterTag(val tag: TrainingTag) : ExerciseListStateUpdater
    data class RemoveFilterTag(val tag: TrainingTag) : ExerciseListStateUpdater
}