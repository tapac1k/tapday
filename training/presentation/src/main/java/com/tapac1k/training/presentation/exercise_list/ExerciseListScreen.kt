package com.tapac1k.training.presentation.exercise_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.LifecycleEffect
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBarWithSearch
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.contract_ui.ExerciseListNavigation
import com.tapac1k.training.presentation.training_details.TrainingDetailsUpdater
import com.tapac1k.training.presentation.training_details.TrainingDetailsViewModel
import com.tapac1k.training.presentation.widget.ExerciseItem

@Composable
fun ExerciseListScreen(
    viewModel: ExerciseListViewModel = viewModel(),
    navigation: ExerciseListNavigation
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExerciseListScreenContent(
        state = state,
        onExerciseClick = { navigation.openExerciseDetails(it.id) },
        onAddExerciseClick = navigation::openCreateExercise,
        onTagClick = {},
        onBack = navigation::onBack,
        updater = viewModel::updateState
    )
}

@Composable
fun ExerciseSelectionScreen(
    trainingDetailsViewModel: TrainingDetailsViewModel = viewModel(),
    viewModel: ExerciseListViewModel = viewModel(),
    navigation: ExerciseListNavigation
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExerciseListScreenContent(
        state = state,
        onExerciseClick = {
            trainingDetailsViewModel.updateState(TrainingDetailsUpdater.AddExercise(it))
            navigation.onBack()
        },
        onAddExerciseClick = navigation::openCreateExercise,
        onTagClick = {},
        onBack = navigation::onBack,
        updater = viewModel::updateState
    )
}

@Composable
fun ExerciseListScreenContent(
    state: ExerciseListState = ExerciseListState(),
    onExerciseClick: (Exercise) -> Unit = {},
    onAddExerciseClick: () -> Unit = {},
    onTagClick: (TrainingTag) -> Unit = {},
    onBack: () -> Unit = {},
    updater: (ExerciseListStateUpdater) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBarWithSearch(
                state.query,
                onBack = onBack,
                onQueryChange = { updater(ExerciseListStateUpdater.UpdateQuery(it)) },
                onClear = { updater(ExerciseListStateUpdater.ClearQuery) }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = onAddExerciseClick
            ) {
                Text("Create Exercise", modifier = Modifier.padding(10.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(state.exercises.count(), { state.exercises[it].id }) {
                val exercise = state.exercises[it]
                ExerciseItem(
                    state.exercises[it],
                    onExerciseClick = { onExerciseClick.invoke(exercise) },
                    onTagClick = onTagClick
                )
            }
        }
    }
}

@Composable
fun ExerciseListScreenPreview() {
    TapMyDayTheme {
        ExerciseListScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ExerciseListScreenPreviewLight() {
    ExerciseListScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExerciseListScreenPreviewDark() {
    ExerciseListScreenPreview()
}
