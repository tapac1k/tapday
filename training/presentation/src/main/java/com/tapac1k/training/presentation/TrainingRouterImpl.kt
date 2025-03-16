package com.tapac1k.training.presentation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.tapac1k.training.contract_ui.TrainingRouter
import com.tapac1k.training.contract_ui.TrainingTagNavigation
import com.tapac1k.training.presentation.tag.TagDialogScreen
import com.tapac1k.training.presentation.tag.TrainingTagDialogViewModel
import com.tapac1k.training.presentation.tags.TrainingTagsScreen
import com.tapac1k.training.presentation.tags.TrainingTagsViewModel
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
}