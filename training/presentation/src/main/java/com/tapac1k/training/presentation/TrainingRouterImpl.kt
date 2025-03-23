package com.tapac1k.training.presentation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import com.tapac1k.training.contract_ui.ExerciseSelectionRoute
import com.tapac1k.training.contract_ui.TrainingDetailsRoute
import com.tapac1k.training.contract_ui.TrainingListNavigation
import com.tapac1k.training.contract_ui.TrainingListRoute
import com.tapac1k.training.contract_ui.TrainingRouter
import com.tapac1k.training.contract_ui.TrainingTagNavigation
import com.tapac1k.training.contract_ui.TrainingTagRoute
import com.tapac1k.training.contract_ui.TrainingTagsRoute
import com.tapac1k.training.presentation.exercise.ExerciseDetailsScreen
import com.tapac1k.training.presentation.exercise_list.ExerciseListScreen
import com.tapac1k.training.presentation.exercise_list.ExerciseSelectionScreen
import com.tapac1k.training.presentation.tag.TagDialogScreen
import com.tapac1k.training.presentation.tags.TrainingTagsScreen
import com.tapac1k.training.presentation.tags.TrainingTagsViewModel
import com.tapac1k.training.presentation.training_details.TrainingDetailsScreen
import com.tapac1k.training.presentation.training_details.TrainingDetailsViewModel
import com.tapac1k.training.presentation.training_list.TrainingListScreen
import com.tapac1k.utils.common.WithBackNavigation
import javax.inject.Inject

class TrainingRouterImpl @Inject constructor(

) : TrainingRouter {

    override fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation) {

        composable<TrainingTagsRoute> {
            TrainingTagsScreen(hiltViewModel(), object : TrainingTagNavigation, WithBackNavigation by defaultBackController {
                override fun createTag() {
                    navController.safeNavigate(TrainingTagsRoute::class, TrainingTagRoute())
                }

                override fun ediTag(tag: TrainingTag) {
                    navController.safeNavigate(TrainingTagsRoute::class, TrainingTagRoute(tag.id, tag.value))
                }
            })
        }
        composable<ExerciseListRoute> {
            ExerciseListScreen(hiltViewModel(), object : ExerciseListNavigation, WithBackNavigation by defaultBackController {
                override fun openExerciseDetails(exerciseId: String) {
                    navController.safeNavigate(ExerciseListRoute::class, ExerciseDetailsRoute(exerciseId))
                }

                override fun openCreateExercise() {
                    navController.safeNavigate(ExerciseListRoute::class, ExerciseDetailsRoute())
                }
            })
        }
        composable<ExerciseDetailsRoute> {
            ExerciseDetailsScreen(hiltViewModel(), defaultBackController)
        }
        composable<TrainingListRoute> {
            TrainingListScreen(
                hiltViewModel(),
                object : TrainingListNavigation {
                    override fun createTraining() {
                        navController.safeNavigate(TrainingListRoute::class, TrainingDetailsRoute(null))
                    }

                    override fun openTraining(trainingId: String) {
                        navController.safeNavigate(TrainingListRoute::class, TrainingDetailsRoute(trainingId))
                    }
                }
            )
        }
        composable<TrainingDetailsRoute> {
            TrainingDetailsScreen(
                hiltViewModel(),
                onBack = navController::navigateUp,
                onChooseExercise = {
                    navController.safeNavigate(TrainingDetailsRoute::class, ExerciseSelectionRoute())
                }
            )
        }
        composable<ExerciseSelectionRoute> {
            val parentEntry = remember(it) {
                navController.getBackStackEntry<TrainingDetailsRoute>()
            }
            val viewModel = hiltViewModel<TrainingDetailsViewModel>(parentEntry)
            ExerciseSelectionScreen(
                viewModel,
                hiltViewModel(),
                object : ExerciseListNavigation, WithBackNavigation by defaultBackController {
                    override fun openExerciseDetails(exerciseId: String) {
                        TODO()
                    }

                    override fun openCreateExercise() {
                        navController.safeNavigate(ExerciseSelectionRoute::class, ExerciseDetailsRoute())
                    }
                }
            )
        }
        dialog<TrainingTagRoute> {
            TagDialogScreen(hiltViewModel(), defaultBackController)
        }
    }

}