package com.tapac1k.settings.contract_ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.tapac1k.utils.common.WithBackNavigation

interface SettingsRouter {
    fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation)
}