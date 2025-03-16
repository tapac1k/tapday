package com.tapac1k.training.contract_ui

import androidx.compose.runtime.Composable
import com.tapac1k.utils.common.WithBackNavigation

interface TrainingRouter {
    @Composable
    fun NavigateTrainingTags(navigation: TrainingTagNavigation)

    @Composable
    fun NavigateTrainingTag(navigation: WithBackNavigation)
}