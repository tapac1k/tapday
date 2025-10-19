package com.tapac1k.training.presentation.training_details

import android.content.res.Configuration
import android.widget.Space
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.LifecycleEffect
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.presentation.tag.TrainingTagEvent
import com.tapac1k.training.presentation.widget.ExerciseGroupItem
import com.tapac1k.training.presentation.widget.dateFormat
import com.tapac1k.training.presentation.widget.dateFullFormat
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TrainingDetailsScreen(
    viewModel: TrainingDetailsViewModel = viewModel(),
    onBack: () -> Unit = { },
    onNewExerciseGroup: () -> Unit = { },
    onExerciseHistory: (String) -> Unit = { },
    onReplaceExercise:  (String) -> Unit = { },
    showToast: (String) -> Unit = { }
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LifecycleEffect(LocalLifecycleOwner.current, onPause = {
        viewModel.saveTraining()
    })
    LaunchedEffect("events") {
        viewModel.events.collectLatest {
            when(it) {
                TrainingDetailsEvent.Finish -> onBack()
                is TrainingDetailsEvent.ReplaceExercise -> onReplaceExercise(it.exerciseGroupId)
                is TrainingDetailsEvent.ShowToast -> showToast(it.message)
            }
        }
    }
    state.dialogState?.let {
        TrainingDetailsDispatchDialog(it, viewModel::requestUpdateState)
    }
    TrainingDetailsScreenContent(
        state = state,
        onBack = onBack,
        onAddExercise = onNewExerciseGroup,
        updater = viewModel::requestUpdateState,
        onExerciseHistory = onExerciseHistory
    )
}

@Composable
fun TrainingDetailsDispatchDialog(
    state: DialogState,
    updater: (TrainingDetailsUpdater) -> Unit = {},
) {
    val onDismiss = { updater.invoke(TrainingDetailsUpdater.DismissDialogs) }
    when (state) {
        is DialogState.ConfirmRemoveExerciseGroup -> TODO()
        is DialogState.SetupSet -> {
            SetupSetDialog(
                set = state.set,
                onDismiss = onDismiss,
                onSave = { id: String?, weight: Float?, reps: Int?, time: Int? ->
                    updater.invoke(
                        TrainingDetailsUpdater.SetupSetConfirm(
                            exerciseGroupId = state.exerciseGroupId,
                            setId = id,
                            weight = weight,
                            reps = reps,
                            time = time
                        )
                    )
                },
                withWeight = state.withWeight,
                timeBased = state.timeBased
            )
        }

        is DialogState.DatePicker -> {
            SetupDateDialog(
                startDate = state.date,
                onDismiss = onDismiss,
                onSaveDate = { updater.invoke(TrainingDetailsUpdater.UpdateDate(it)) }
            )
        }

        is DialogState.SetDescriptionDialog -> {
            SetDescriptionDialog(
                text = state.description,
                onDismiss = onDismiss,
                onSave = { updater.invoke(TrainingDetailsUpdater.UpdateDescription(it)) }
            )
        }
    }
}

@Composable
fun TrainingDetailsScreenContent(
    state: TrainingDetailsState = TrainingDetailsState(),
    onBack: () -> Unit = { },
    onAddExercise: () -> Unit = { },
    updater: (TrainingDetailsUpdater) -> Unit = {},
    onExerciseHistory: (String) -> Unit = { },
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
                Column(Modifier.weight(1f)) {
                    val date = state.date
                    Text(dateFormat.format(date).capitalize(Locale.current), style = MaterialTheme.typography.headlineSmall)
                    Text(dateFullFormat.format(date), style = MaterialTheme.typography.labelSmall)
                }
                IconButton(onClick = { updater.invoke(TrainingDetailsUpdater.ShowDatePicker) }) {
                    Icon(Icons.Filled.EditCalendar, "Set date")
                }
                IconButton(onClick = { updater.invoke(TrainingDetailsUpdater.ShowDescriptionDialog) }, Modifier.alpha(if (state.description.isNotBlank()) 1f else 0.5f)) {
                    Icon(Icons.Filled.Description, "Set description")
                }
            }
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = onAddExercise
            ) {
                Text("Choose Exercise", modifier = Modifier.padding(10.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            items(state.exercises.count(), { state.exercises[it].id }) {
                val exercise = state.exercises[it]
                val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = {

                    false
                })
                SwipeToDismissBox(state = dismissState, backgroundContent = {
                    Spacer(Modifier.size(20.dp))
                    IconButton(onClick = {
                        updater.invoke(TrainingDetailsUpdater.RequestReplaceExercise(exercise.id))
                    }, modifier = Modifier.align(Alignment.CenterVertically).padding(20.dp).size(20.dp)) {
                        Icon(Icons.Rounded.Edit, contentDescription = "Edit Exercise",)
                    }

                    IconButton(onClick = {
                        updater.invoke(TrainingDetailsUpdater.ConfirmRemoveExercise(exercise.id))
                    }, modifier = Modifier.align(Alignment.CenterVertically).padding(20.dp).size(20.dp)) {
                        Icon(Icons.Rounded.Delete, contentDescription = "Delete Exercise",)
                    }
                }, modifier = Modifier.fillMaxWidth()) {
                    ExerciseGroupItem(
                        exerciseGroup = exercise,
                        isHistoryItem = false,
                        onAddSet = {
                            updater.invoke(TrainingDetailsUpdater.AddSet(exercise))
                        },
                        onEditSet = { set ->
                            updater.invoke(TrainingDetailsUpdater.EditSet(exercise, set))
                        },
                        onExerciseClick = {
                            onExerciseHistory(exercise.exercise.id)
                        }
                    )
                }

            }

        }
    }
}


@Composable
fun TrainingDetailsScreenPreview() {
    TapMyDayTheme {
        TrainingDetailsScreenContent(
            TrainingDetailsState(
                exercises = listOf(
                    ExerciseGroup(
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
                            ExerciseSet("1", 1f, null, 1),
                            ExerciseSet("2", 1f, 1, 1),
                            ExerciseSet("3", 1f, 1, 1),
                        )
                    )
                )
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TrainingDetailsScreenPreviewLight() {
    TrainingDetailsScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TrainingDetailsScreenPreviewDark() {
    TrainingDetailsScreenPreview()
}
