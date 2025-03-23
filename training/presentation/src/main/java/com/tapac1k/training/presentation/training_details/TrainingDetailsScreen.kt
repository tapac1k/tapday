package com.tapac1k.training.presentation.training_details

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
import com.tapac1k.training.presentation.widget.ExerciseSetItem

@Composable
fun TrainingDetailsScreen(
    viewModel: TrainingDetailsViewModel = viewModel(),
    onBack: () -> Unit = { },
    onChooseExercise: () -> Unit = { },
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LifecycleEffect(LocalLifecycleOwner.current, onStop = {
        viewModel.saveTraining()
    })
    state.dialogState?.let {
        TrainingDetailsDispatchDialog(it, viewModel::updateState)
    }
    TrainingDetailsScreenContent(
        state = state,
        onBack = onBack,
        onAddExercise = onChooseExercise,
        updater = viewModel::updateState
    )
}

@Composable
fun TrainingDetailsDispatchDialog(
    state: DialogState,
    updater: (TrainingDetailsUpdater) -> Unit = {},
) {
    when (state) {
        is DialogState.ConfirmRemoveExerciseGroup -> TODO()
        is DialogState.SetupSet -> {
            SetupSetDialog(
                set = state.set,
                onDismiss = { updater.invoke(TrainingDetailsUpdater.DismissDialogs) },
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
    }
}

@Composable
fun TrainingDetailsScreenContent(
    state: TrainingDetailsState = TrainingDetailsState(),
    onBack: () -> Unit = { },
    onAddExercise: () -> Unit = { },
    updater: (TrainingDetailsUpdater) -> Unit = {}
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
                Spacer(Modifier.weight(1f))
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
                ExerciseGroupItem(
                    exerciseGroup = state.exercises[it],
                    onAddSet = {
                        updater.invoke(TrainingDetailsUpdater.AddSet(state.exercises[it]))
                    },
                    onEditSet = { set ->
                        updater.invoke(TrainingDetailsUpdater.EditSet(state.exercises[it], set))
                    }
                )
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseGroupItem(
    exerciseGroup: ExerciseGroup,
    onAddSet: () -> Unit = {},
    onEditSet: (ExerciseSet) -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = exerciseGroup.exercise.name, Modifier.padding(4.dp), style = MaterialTheme.typography.labelLarge)
        FlowRow(Modifier.padding(4.dp), ) {

            exerciseGroup.sets.forEach{
                ExerciseSetItem(exerciseGroup.exercise.withWeight, exerciseGroup.exercise.timeBased, it, Modifier.padding(4.dp).height(30.dp).clickable {
                    onEditSet(it)
                })
            }
            Box(
                modifier = Modifier
                    .clickable { onAddSet() }.padding(4.dp).size(40.dp, 30.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = RoundedCornerShape(percent = 100)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "+", style = MaterialTheme.typography.labelLarge,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary
                )
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
                        sets = listOf(
                            ExerciseSet("1", 1f, 1, 1),
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
