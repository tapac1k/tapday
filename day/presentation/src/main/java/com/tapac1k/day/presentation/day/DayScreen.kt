package com.tapac1k.day.presentation.day

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.LifecycleEffect
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.presentation.widget.ActivityRings
import com.tapac1k.day.presentation.widget.ActivityView

@Composable
fun DayScreen(
    viewModel: DayViewModel = viewModel(),
    dayNavigation: DayNavigation
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    LifecycleEffect(
        lifecycleOwner,
        onStop = {
            viewModel.saveDay()
        }
    )
    DayScreenContent(
        state,
        stateUpdater = viewModel::requestUpdateState,
        onBack = dayNavigation::onBack,
    )
}

@Composable
fun DayScreenContent(
    dayState: DayState = DayState(),
    stateUpdater: (StateUpdate) -> Unit = {},
    onBack: () -> Unit = {},
) {
    var editActivity by rememberSaveable { mutableStateOf(false) }
    val collapsedPositiveHabits = rememberSaveable { mutableStateOf(true) }
    val collapsedNegativeHabits = rememberSaveable { mutableStateOf(true) }
    var collapsedDescription by rememberSaveable { mutableStateOf(false) }
    Scaffold(
        Modifier
            .fillMaxSize()
            .imePadding(), topBar = {
            TopBar {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
                }
                Spacer(Modifier.weight(1f))
                ActivityView(
                    dayState.dayActivity, Modifier
                        .padding(end = 16.dp)
                        .clickable { editActivity = !editActivity }
                        .size(30.dp))
            }
        }) { padding ->
        LazyColumn(
            contentPadding = padding, modifier = Modifier
                .fillMaxSize()
                .consumeWindowInsets(padding)
        ) {
            item("Rings") {
                if (editActivity) {
                    ActivityRings(
                        dayState.dayActivity,
                        onMoodUpdate = { stateUpdater(StateUpdate.UpdateMood(it)) },
                        onStateUpdate = { stateUpdater(StateUpdate.UpdateState(it)) },
                        onSleepUpdate = { stateUpdater(StateUpdate.UpdateSleepHours(it)) },
                        modifier = Modifier
                            .animateItem(placementSpec = null)
                    )
                }

            }
            item("Description") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Description", modifier = Modifier
                            .padding(16.dp)
                            .weight(1f), style = MaterialTheme.typography.headlineSmall
                    )
                    CollapseIcon(collapsedDescription, onClick = {
                        collapsedDescription = !collapsedDescription
                    })
                }
                HorizontalDivider()
                if (!collapsedDescription) {
                    DescriptionText(
                        dayState.description, Modifier
                            .animateItem(placementSpec = null)
                            .padding(horizontal = 16.dp)
                    ) {
                        stateUpdater(StateUpdate.UpdateDescription(it))
                    }
                }
            }
            item("Good habits") {
                HabitTitle("Good habits", collapsedPositiveHabits) {
                    stateUpdater(StateUpdate.AddHabit(Habit("", "", true)))
                }
            }
            if (!collapsedPositiveHabits) {
                items(dayState.positive.size, { dayState.positive[it].habit.id }) { index ->
                    val habit = dayState.positive[index]
                    Row(
                        Modifier
                            .animateItem()
                            .clickable { stateUpdater.invoke(StateUpdate.ToggleHabitState(habit.habit)) }) {
                        Text(
                            habit.habit.name.capitalize(Locale.current),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                        for (i in 0 until habit.state) {
                            Icon(
                                Icons.Rounded.Check,
                                "",
                                modifier = Modifier.padding(4.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                }
            }

            item("Bad habits") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Bad habits", modifier = Modifier
                            .padding(16.dp)
                            .weight(1f), style = MaterialTheme.typography.headlineSmall
                    )
                    IconButton(onClick = { /* TODO */ }) {
                        Icon(Icons.Rounded.Add, "")
                    }
                    CollapseIcon(collapsedNegativeHabits, onClick = {
                        collapsedNegativeHabits = !collapsedNegativeHabits
                    })

                }
                HorizontalDivider()
            }
            if (!collapsedNegativeHabits) {
                items(dayState.negative.size, { dayState.negative[it].habit.id }) { index ->
                    val habit = dayState.negative[index]
                    Row(
                        Modifier
                            .animateItem()
                            .clickable { stateUpdater.invoke(StateUpdate.ToggleHabitState(habit.habit)) }) {
                        Text(
                            habit.habit.name.capitalize(Locale.current),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                        )
                        for (i in 0 until habit.state) {
                            Icon(
                                Icons.Rounded.Check,
                                "",
                                modifier = Modifier.padding(4.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

        }
    }
}

@Composable
private fun HabitTitle(
    title: String,
    collapsedState: MutableState<Boolean>,
    onPlusClick: () -> Unit = {},
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            title
            , modifier = Modifier
                .padding(16.dp)
                .weight(1f), style = MaterialTheme.typography.headlineSmall
        )
        IconButton(onClick = { onPlusClick() }) {
            Icon(Icons.Rounded.Add, "")
        }
        CollapseIcon(collapsedState.value, onClick = {
            collapsedState.value = !collapsedState.value
        })
    }
    HorizontalDivider()
}

@Composable
private fun CollapseIcon(
    collapsed: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    IconButton(onClick = onClick) {
        Icon(if (collapsed) Icons.Rounded.KeyboardArrowRight else Icons.Rounded.KeyboardArrowDown, "")
    }
}

@Composable
private fun DescriptionText(
    description: TextFieldValue,
    modifier: Modifier = Modifier,
    updateText: (TextFieldValue) -> Unit = {},
) {
    OutlinedTextField(
        value = description,
        onValueChange = {
            updateText(it)
        },
        keyboardOptions = KeyboardOptions(autoCorrectEnabled = false, capitalization = KeyboardCapitalization.Sentences, keyboardType = KeyboardType.Text),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        minLines = 5
    )
}


@Preview
@Composable
fun PreviewDayScreen() {
    TapMyDayTheme {
        DayScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDayScreenDark() {
    TapMyDayTheme {
        DayScreenContent()
    }
}