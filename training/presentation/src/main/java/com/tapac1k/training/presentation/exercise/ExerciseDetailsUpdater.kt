package com.tapac1k.training.presentation.exercise

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.training.contract.TrainingTag

sealed interface ExerciseDetailsUpdater {
    data class UpdateName(val name: TextFieldValue) : ExerciseDetailsUpdater
    data class SetWithWeight(val withWeight: Boolean) : ExerciseDetailsUpdater
    data class SetWithTime(val withTime: Boolean) : ExerciseDetailsUpdater
    data class AddTag(val trainingTag: TrainingTag)  : ExerciseDetailsUpdater
    data class RemoveTag(val trainingTag: TrainingTag) : ExerciseDetailsUpdater
    data class UpdateTagQuery(val tagQuery: TextFieldValue) : ExerciseDetailsUpdater
    data object CreateAndAddTag : ExerciseDetailsUpdater
}