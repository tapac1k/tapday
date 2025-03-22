package com.tapac1k.training.contract_ui

import com.tapac1k.utils.common.WithBackNavigation

interface ExerciseListNavigation: WithBackNavigation {
    fun openExerciseDetails(exerciseId: String)
    fun openCreateExercise()
}