package com.tapac1k.day.presentation.habit_list

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBarWithSearch
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.presentation.habit_dialog.HabitListNavigation

@Composable
fun HabitListScreen(
    viewModel: HabitListViewModel = viewModel(),
    habitListNavigation: HabitListNavigation
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HabitListScreenContent(
        state = state,
        newHabit = { habitListNavigation.creteHabit() },
        editHabit = { habitListNavigation.editHabit(it) },
        updater = { viewModel.requestUpdateState(it) },
        onBack = habitListNavigation::onBack
    )
}

@Composable
fun HabitListScreenContent(
    state: HabitListState,
    newHabit: () -> Unit = {},
    editHabit: (Habit) -> Unit = {},
    updater: (HabitListUpdater) -> Unit = {},
    onBack: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TopBarWithSearch(
                query = state.query,
                showAdditionalFilters = false,
                onQueryChange = {
                    updater(
                        HabitListUpdater.UpdateQuery(it)
                    )
                },
                onClear = {
                    updater(HabitListUpdater.ClearQuery)
                },
                onBack = onBack
            )
        },

        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = newHabit
            ) {
                Text("Create habit", modifier = Modifier.padding(10.dp))
            }
        },
    ) { paddingValues ->
        val positive = state.positiveHabits
        val negative = state.negativeHabits
        LazyColumn(contentPadding = paddingValues) {
            if (positive.isNotEmpty()) {
                item {
                    Text(
                        text = "Positive habits",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    HorizontalDivider()
                }
                items(positive.size, {
                    positive[it].id
                }) {
                    val habit = positive[it]
                    Text(
                        habit.name.capitalize(Locale.current),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { editHabit.invoke(habit) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
                item {
                    Spacer(Modifier.height(16.dp))
                }
            }
            if (negative.isNotEmpty()) {
                item {
                    Text(
                        text = "Negative habits",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        style = MaterialTheme.typography.headlineSmall
                    )
                    HorizontalDivider()
                }
                items(negative.size, {
                    negative[it].id
                }) {
                    val habit = negative[it]
                    Text(
                        habit.name.capitalize(Locale.current),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { editHabit.invoke(habit) }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                    )
                }
            }
        }
    }
}

@Composable
fun HabitListScreenPreview() {
    TapMyDayTheme {
        HabitListScreenContent(HabitListState(listOf(Habit("1", "1", true), Habit("12", "12", false)))) {
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun HabitListScreenPreviewLight() {
    HabitListScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun HabitListScreenPreviewDark() {
    HabitListScreenPreview()
}
