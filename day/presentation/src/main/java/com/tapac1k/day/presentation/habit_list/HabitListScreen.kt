package com.tapac1k.day.presentation.habit_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.domain.models.Habit
import com.tapac1k.day.presentation.habit_dialog.HabitListNavigation

@Composable
fun HabitListScreen(
    viewModel: HabitListViewModel = viewModel(),
    habitListNavigation: HabitListNavigation
) {
    HabitListScreenContent(
        newHabit = { habitListNavigation.creteHabit() },
        editHabit = { habitListNavigation.editHabit(it) }
    )
}

@Composable
fun HabitListScreenContent(
    newHabit: () -> Unit = {},
    editHabit: (Habit) -> Unit = {},
) {
    Scaffold(
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
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) { }
    }
}

@Composable
fun HabitListScreenPreview() {
    TapMyDayTheme {
        HabitListScreenContent()
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
