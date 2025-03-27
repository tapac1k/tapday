package com.tapac1k.training.presentation.training_details

import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet

sealed interface TrainingDetailsUpdater {
    sealed interface ChangeTraingingUpdater : TrainingDetailsUpdater
    data class AddExercise(
        val exercise: Exercise,
    ) : ChangeTraingingUpdater

    data class AddSet(
        val exerciseGroup: ExerciseGroup
    ) : TrainingDetailsUpdater

    data class EditSet(
        val exerciseGroup: ExerciseGroup,
        val exerciseSet: ExerciseSet,
    ) : TrainingDetailsUpdater

    data object DeleteSet : TrainingDetailsUpdater
    data object RemoveExercise : TrainingDetailsUpdater
    data object ConfirmRemoveExercise : ChangeTraingingUpdater
    data object DismissDialogs : TrainingDetailsUpdater

    data class UpdateDescription(
        val description: String,
    ) : ChangeTraingingUpdater

    data class UpdateDate(
        val date: Long,
    ) : ChangeTraingingUpdater

    data class SetupSetConfirm(
        val exerciseGroupId: String,
        val setId: String?,
        val time: Int?,
        val reps: Int?,
        val weight: Float?,
    ) : ChangeTraingingUpdater

    data object ShowDatePicker : TrainingDetailsUpdater
    data object ShowDescriptionDialog : TrainingDetailsUpdater
}
