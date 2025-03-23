package com.tapac1k.training.contract_ui

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import com.tapac1k.utils.common.WithBackNavigation

interface TrainingRouter {
    fun NavGraphBuilder.initGraph(navController: NavController, defaultBackController: WithBackNavigation)
}