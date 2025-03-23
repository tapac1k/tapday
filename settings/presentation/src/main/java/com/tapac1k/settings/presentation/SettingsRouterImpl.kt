package com.tapac1k.settings.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tapac1k.compose.safeNavigate
import com.tapac1k.settings.contract_ui.SettingsNavigation
import com.tapac1k.settings.contract_ui.SettingsRoute
import com.tapac1k.settings.contract_ui.SettingsRouter
import com.tapac1k.utils.common.WithBackNavigation
import javax.inject.Inject

class SettingsRouterImpl @Inject constructor(

) : SettingsRouter {


    override fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation) {
        composable<SettingsRoute> { navBackStackEntry ->
            SettingsScreen(hiltViewModel(), object : SettingsNavigation {
                override fun navigateTo(route: Any) {
                    navController.safeNavigate(SettingsRoute::class, route)
                }
            })
        }
    }
}