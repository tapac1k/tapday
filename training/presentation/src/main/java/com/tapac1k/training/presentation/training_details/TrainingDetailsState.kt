package com.tapac1k.training.presentation.training_details

import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet

data class TrainingDetailsState(
    val loading: Boolean = true,
    val exercises: List<ExerciseGroup> = emptyList(),
    val dialogState: DialogState? = null,
    val date: Long = System.currentTimeMillis(),
    val description: String = "",
)

sealed interface DialogState {
    data class ConfirmRemoveExerciseGroup(val groupId: Long) : DialogState
    data class DatePicker(val date: Long) : DialogState
    data class SetDescriptionDialog(val description: String) : DialogState
    data class SetupSet(
        val exerciseGroupId: String,
        val withWeight: Boolean,
        val timeBased: Boolean,
        val set: ExerciseSet?,
    ) : DialogState
}
