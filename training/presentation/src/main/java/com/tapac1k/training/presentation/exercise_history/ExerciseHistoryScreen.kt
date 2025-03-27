package com.tapac1k.training.presentation.exercise_history

import android.content.res.Configuration
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.presentation.widget.ExerciseGroupItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun ExerciseHistoryScreen(
    viewModel: ExerciseHistoryViewModel = viewModel(),
    onBack: () -> Unit = { },
    openTraining: (String) -> Unit = { },
) {
    val paging = viewModel.pagingData.collectAsLazyPagingItems()
    val state by viewModel.state.collectAsStateWithLifecycle()
    ExerciseHistoryScreenContent(
        state = state,
        paging = paging,
        onBack = onBack,
        openTraining = openTraining
    )
}

@Composable
fun ExerciseHistoryScreenContent(
    state: ExerciseHistoryState,
    paging: LazyPagingItems<Pair<String, ExerciseGroup>>,
    onBack: () -> Unit = {},
    openTraining: (String) -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBar(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
                }
                Text(state.exerciseName)
                Spacer(Modifier.weight(1f))
            }
        },
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(paging.itemCount, {
                paging.get(it)?.first + paging.get(it)?.second?.id
            }) { index ->
                val item = paging[index]
                if (item != null) {
                    ExerciseGroupItem(item.second,
                        isHistoryItem = true,
                        onExerciseClick = { openTraining(item.first) }
                    )
                }
            }
        }
    }
}

@Composable
fun ExerciseHistoryScreenPreview() {
    TapMyDayTheme {
        ExerciseHistoryScreenContent(
            state = ExerciseHistoryState(exerciseName = "Exercise 1"),
            paging = flowOf(
                PagingData.from(
                    listOf(
                        "1" to ExerciseGroup(
                            id = "1",
                            exercise = Exercise(
                                id = "asd",
                                name = "Exercise 2",
                                tags = listOf(
                                    TrainingTag("1", "Груди"),
                                    TrainingTag("2", "Біцепс"),
                                    TrainingTag("3", "Пяна мамка"),
                                    TrainingTag("4", "asd a"),
                                    TrainingTag("5", "Basdas sad as"),
                                ),
                                withWeight = true,
                                timeBased = false
                            ),
                            date = 1243L,
                            sets = listOf(
                                ExerciseSet("1", 1f, 1, 1),
                                ExerciseSet("2", 1f, 1, 1),
                                ExerciseSet("3", 1f, 1, 1),
                            )
                        )
                    )
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ExerciseHistoryScreenPreviewLight() {
    ExerciseHistoryScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExerciseHistoryScreenPreviewDark() {
    ExerciseHistoryScreenPreview()
}
