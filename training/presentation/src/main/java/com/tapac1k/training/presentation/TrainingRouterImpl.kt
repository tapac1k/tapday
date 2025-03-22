package com.tapac1k.training.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.tapac1k.compose.safeNavigate
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.contract_ui.ExerciseDetailsRoute
import com.tapac1k.training.contract_ui.ExerciseListNavigation
import com.tapac1k.training.contract_ui.ExerciseListRoute
import com.tapac1k.training.contract_ui.TrainingDetails
import com.tapac1k.training.contract_ui.TrainingListNavigation
import com.tapac1k.training.contract_ui.TrainingListRoute
import com.tapac1k.training.contract_ui.TrainingRouter
import com.tapac1k.training.contract_ui.TrainingTagNavigation
import com.tapac1k.training.contract_ui.TrainingTagRoute
import com.tapac1k.training.contract_ui.TrainingTagsRoute
import com.tapac1k.training.presentation.exercise.ExerciseDetailsScreen
import com.tapac1k.training.presentation.exercise.ExerciseDetailsViewModel
import com.tapac1k.training.presentation.exercise_list.ExerciseListScreen
import com.tapac1k.training.presentation.exercise_list.ExerciseListViewModel
import com.tapac1k.training.presentation.tag.TagDialogScreen
import com.tapac1k.training.presentation.tag.TrainingTagDialogViewModel
import com.tapac1k.training.presentation.tags.TrainingTagsScreen
import com.tapac1k.training.presentation.tags.TrainingTagsViewModel
import com.tapac1k.training.presentation.training_list.TrainingListScreen
import com.tapac1k.training.presentation.training_list.TrainingListViewModel
import com.tapac1k.utils.common.WithBackNavigation
import javax.inject.Inject

class TrainingRouterImpl @Inject constructor(

) : TrainingRouter {

    @Composable
    override fun NavigateTrainingTags(navigation: TrainingTagNavigation) {
        val viewModel = hiltViewModel<TrainingTagsViewModel>()
        TrainingTagsScreen(viewModel, navigation)
    }

    @Composable
    override fun NavigateTrainingTag(navigation: WithBackNavigation) {
        val viewModel = hiltViewModel<TrainingTagDialogViewModel>()
        TagDialogScreen(viewModel, navigation)
    }

    @Composable
    override fun NavigateExerciseList(navigation: ExerciseListNavigation) {
        val viewModel = hiltViewModel<ExerciseListViewModel>()
        ExerciseListScreen(viewModel, navigation)
    }

    @Composable
    override fun NavigateExerciseDetails(navigation: WithBackNavigation) {
        val viewModel = hiltViewModel<ExerciseDetailsViewModel>()
        ExerciseDetailsScreen(viewModel, navigation)
    }

    @Composable
    override fun NavigateTrainingList(navigation: TrainingListNavigation) {
        val viewModel = hiltViewModel<TrainingListViewModel>()
        TrainingListScreen(viewModel, navigation)
    }

    override fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation) {

        composable<TrainingTagsRoute>{
            NavigateTrainingTags(object : TrainingTagNavigation, WithBackNavigation by defaultBackController {
                override fun createTag() {
                    navController.safeNavigate(TrainingTagsRoute::class, TrainingTagRoute())
                }

                override fun ediTag(tag: TrainingTag) {
                    navController.safeNavigate(TrainingTagsRoute::class, TrainingTagRoute(tag.id, tag.value))
                }
            })
        }
        composable<ExerciseListRoute> {
            NavigateExerciseList(
                object : ExerciseListNavigation, WithBackNavigation by defaultBackController {
                    override fun openExerciseDetails(exerciseId: String) {
                        navController.safeNavigate(ExerciseListRoute::class, ExerciseDetailsRoute(exerciseId))
                    }

                    override fun openCreateExercise() {
                        navController.safeNavigate(ExerciseListRoute::class, ExerciseDetailsRoute())
                    }
                }
            )
        }
        composable<ExerciseDetailsRoute> {
            NavigateExerciseDetails(defaultBackController)
        }
        composable<TrainingListRoute> {
            NavigateTrainingList(object : TrainingListNavigation {
                override fun createTraining() {
                    navController.safeNavigate(TrainingListRoute::class, TrainingDetails(null))
                }

                override fun openTraining(trainingId: String) {
                    TODO("Not yet implemented")
                }
            })
        }
        dialog<TrainingTagRoute> {
            NavigateTrainingTag(defaultBackController)
        }
    }

}