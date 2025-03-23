package com.tapac1k.day.contract_ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.tapac1k.utils.common.WithBackNavigation

interface DayRouter {
    fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation)
}