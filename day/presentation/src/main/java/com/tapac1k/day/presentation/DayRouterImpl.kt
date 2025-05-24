package com.tapac1k.day.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.tapac1k.compose.safeNavigate
import com.tapac1k.day.contract_ui.DayListNavigation
import com.tapac1k.day.contract_ui.DayListRoute
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.contract_ui.DayRoute
import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.day.contract_ui.EditHabitRoute
import com.tapac1k.day.contract_ui.HabitListRoute
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.presentation.day.DayScreen
import com.tapac1k.day.presentation.daylist.DayListScreen
import com.tapac1k.day.presentation.habit_dialog.HabitDialogScreen
import com.tapac1k.day.presentation.habit_dialog.HabitListNavigation
import com.tapac1k.day.presentation.habit_list.HabitListScreen
import com.tapac1k.utils.common.WithBackNavigation
import javax.inject.Inject

class DayRouterImpl @Inject constructor() : DayRouter {

    override fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation) {
        composable<DayListRoute> { backStackEntry ->
            DayListScreen(hiltViewModel(), object : DayListNavigation {
                override fun openDayDetails(dayId: Long) {
                    navController.safeNavigate(DayListRoute::class, DayRoute(dayId))
                }
            })
        }
        composable<DayRoute> {
            DayScreen(hiltViewModel(), object : DayNavigation, WithBackNavigation by defaultBackController {
                override fun onCreateHabit(isGood: Boolean) {
                    navController.safeNavigate(
                        DayListRoute::class, EditHabitRoute(
                            isPositive = isGood
                        )
                    )
                }
            })
        }
        composable<HabitListRoute> {
            HabitListScreen(
                hiltViewModel(),
                object : HabitListNavigation, WithBackNavigation by defaultBackController {
                    override fun creteHabit() {
                        navController.safeNavigate(HabitListRoute::class, EditHabitRoute())
                    }

                    override fun editHabit(habit: Habit) {
                        navController.safeNavigate(
                            HabitListRoute::class, EditHabitRoute(
                                habitId = habit.id,
                                habitName = habit.name,
                                isPositive = habit.isPositive,
                            )
                        )
                    }
                },
            )
        }
        dialog<EditHabitRoute> {
            HabitDialogScreen(hiltViewModel(), defaultBackController)
        }
    }
}