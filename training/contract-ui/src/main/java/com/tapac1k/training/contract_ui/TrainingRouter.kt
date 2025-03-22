package com.tapac1k.training.contract_ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.tapac1k.utils.common.WithBackNavigation

interface TrainingRouter {
    @Composable
    fun NavigateTrainingTags(navigation: TrainingTagNavigation)

    @Composable
    fun NavigateTrainingTag(navigation: WithBackNavigation)

    @Composable
    fun NavigateExerciseList(navigation: ExerciseListNavigation)
    @Composable
    fun NavigateExerciseDetails(navigation: WithBackNavigation)

    @Composable
    fun NavigateTrainingList(navigation: TrainingListNavigation)

    fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation)
}