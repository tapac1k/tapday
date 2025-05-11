package com.tapac1k.training.presentation.exercise_list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBarWithSearch
import com.tapac1k.training.contract.Exercise
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
        onBack = navigation::onBack,
        updater = viewModel::requestUpdateState
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
            trainingDetailsViewModel.requestUpdateState(TrainingDetailsUpdater.AddExercise(it))
            navigation.onBack()
        },
        onAddExerciseClick = navigation::openCreateExercise,
        onBack = navigation::onBack,
        updater = viewModel::requestUpdateState
    )
}

@Composable
fun ExerciseListScreenContent(
    state: ExerciseListState = ExerciseListState(),
    onExerciseClick: (Exercise) -> Unit = {},
    onAddExerciseClick: () -> Unit = {},
    onBack: () -> Unit = {},
    updater: (ExerciseListStateUpdater) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopBarWithSearch(
                state.query,
                showAdditionalFilters = state.tagList.isNotEmpty(),
                extraFilterContent = {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                    ) {
                        items(state.tagList.size, { state.tagList[it].id }) {
                            val tag = state.tagList[it]
                            Row(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clickable { updater.invoke(ExerciseListStateUpdater.RemoveFilterTag(tag)) }
                                    .wrapContentSize()
                                    .background(Color(tag.color).copy(alpha = 0.5f), RoundedCornerShape(percent = 100))
                                    .padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(tag.value, maxLines = 1, style = MaterialTheme.typography.labelSmall)
                                Icon(Icons.Filled.Close, contentDescription = "Remove tag", Modifier.size(12.dp))
                            }
                        }
                    }
                },
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
        val list = state.filteredExercises
        LazyColumn(contentPadding = paddingValues) {
            items(list.count(), { list[it].id }) {
                val exercise = list[it]
                ExerciseItem(
                    exercise,
                    onExerciseClick = { onExerciseClick.invoke(exercise) },
                    onTagClick = { updater.invoke(ExerciseListStateUpdater.AddFilterTag(it)) }
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
