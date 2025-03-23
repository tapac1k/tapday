package com.tapac1k.day.presentation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tapac1k.compose.safeNavigate
import com.tapac1k.day.contract_ui.DayListNavigation
import com.tapac1k.day.contract_ui.DayListRoute
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.contract_ui.DayRoute
import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.day.presentation.daylist.DayListScreen
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
            DayScreen(hiltViewModel(), object : DayNavigation, WithBackNavigation by defaultBackController {})
        }
    }
}