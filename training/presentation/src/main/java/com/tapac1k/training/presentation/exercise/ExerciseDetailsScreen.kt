package com.tapac1k.training.presentation.exercise

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.utils.common.WithBackNavigation
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ExerciseDetailsScreen(
    viewModel: ExerciseDetailsViewModel = viewModel(),
    navigation: WithBackNavigation,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect("events") {
        viewModel.events.collectLatest {
            when(it) {
                is ExerciseDetailsEvent.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is ExerciseDetailsEvent.Finish -> navigation.onBack()
            }
        }
    }

    ExerciseDetailsScreenContent(
        state,
        navigation::onBack,
        viewModel::requestUpdateState,
        viewModel::saveExercise,
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ExerciseDetailsScreenContent(
    state: ExerciseDetailsState,
    onBack: () -> Unit = {},
    updater: (ExerciseDetailsUpdater) -> Unit = {},
    onSave: () -> Unit = {},
) {
    Scaffold(topBar = {
        TopBar(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = onBack) {
                Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
            }
            Spacer(Modifier.weight(1f))
            Button(modifier = Modifier.padding(8.dp).height(32.dp), onClick = onSave, contentPadding = PaddingValues(vertical = 4.dp, horizontal = 2.dp)) {
                Text("Save")
            }
        }
    }) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
        ) {
            ExerciseBody(state, updater)

            HorizontalDivider()
            Column(
                Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                TextField(
                    value = state.tagQuery,
                    modifier = Modifier.padding(8.dp).fillMaxWidth().background(MaterialTheme.colorScheme.surfaceContainer),
                    onValueChange = { updater(ExerciseDetailsUpdater.UpdateTagQuery(it)) },
                    leadingIcon = {
                        Icon(Icons.Filled.Search, "Search Tag")
                    },
                    trailingIcon = {
                        IconButton(onClick = { updater(ExerciseDetailsUpdater.UpdateTagQuery(TextFieldValue(""))) }) {
                            Icon(Icons.Filled.Close, "Clear Search")
                        }
                    }
                )

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    state.filteredTags.forEach { tag ->
                        Row(
                            modifier = Modifier
                                .padding(4.dp)
                                .clickable { updater.invoke(ExerciseDetailsUpdater.AddTag(tag)) }
                                .wrapContentSize()
                                .background(Color(tag.color).copy(alpha = 0.5f), RoundedCornerShape(percent = 100))
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(tag.value, maxLines = 1, style = MaterialTheme.typography.labelSmall)
                            Icon(Icons.Filled.Add, contentDescription = "Remove tag", Modifier.size(12.dp))
                        }
                    }
                }
            }

        }
    }
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun ExerciseBody(
    state: ExerciseDetailsState,
    updater: (ExerciseDetailsUpdater) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        TextField(
            state.name,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { updater(ExerciseDetailsUpdater.UpdateName(it)) },
            placeholder = { Text("Exercise name") }
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(state.withWeight, onCheckedChange = { updater(ExerciseDetailsUpdater.SetWithWeight(it)) })
            Text("with weight")

            Checkbox(state.timeBased, onCheckedChange = { updater(ExerciseDetailsUpdater.SetWithTime(it)) })
            Text("time based")
        }
    }

    HorizontalDivider()

    FlowRow(
        modifier = Modifier.fillMaxWidth()
            .padding(16.dp)
    ) {
        state.selectedTags.forEach { tag ->
            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { updater.invoke(ExerciseDetailsUpdater.RemoveTag(tag)) }
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
}

@Composable
fun ExerciseDetailsScreenPreview() {
    TapMyDayTheme {
        ExerciseDetailsScreenContent(
            ExerciseDetailsState(
                tags = listOf(
                    TrainingTag("1", "Груди"),
                    TrainingTag("2", "Біцепс"),
                    TrainingTag("3", "Пяна мамка"),
                    TrainingTag("4", "asd a"),
                    TrainingTag("5", "Basdas sad as"),
                    TrainingTag("6", "asd a asd"),
                    TrainingTag("7", "ss"),
                ),
                selectedTags = setOf(
                    TrainingTag("1", "Груди"),
                    TrainingTag("2", "Біцепс"),
                    TrainingTag("3", "Пяна мамка"),
                ),
            )
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ExerciseDetailsScreenPreviewLight() {
    ExerciseDetailsScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExerciseDetailsScreenPreviewDark() {
    ExerciseDetailsScreenPreview()
}
