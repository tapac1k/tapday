package com.tapac1k.training.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ExerciseGroup
import com.tapac1k.training.contract.ExerciseSet
import com.tapac1k.training.contract.TrainingTag

private val dateFormatter by lazy {
    java.text.SimpleDateFormat("EEEE (dd.MM.yyyy)", java.util.Locale.getDefault())
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseGroupItem(
    exerciseGroup: ExerciseGroup,
    isHistoryItem: Boolean,
    onAddSet: () -> Unit = {},
    onEditSet: (ExerciseSet) -> Unit = {},
    onExerciseClick: () -> Unit = { },
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(
            text = if (isHistoryItem) dateFormatter.format(exerciseGroup.date).capitalize(Locale.current) else exerciseGroup.exercise.name,
            Modifier
                .padding(4.dp)
                .clickable { onExerciseClick() },
            style = MaterialTheme.typography.labelLarge
        )
        FlowRow(Modifier.padding(4.dp)) {

            exerciseGroup.sets.forEach {
                ExerciseSetItem(
                    exerciseGroup.exercise.withWeight, exerciseGroup.exercise.timeBased, it, Modifier
                        .padding(4.dp)
                        .height(24.dp)
                        .clickable(enabled = !isHistoryItem) {
                            onEditSet(it)
                        })
            }
            if (!isHistoryItem) {
                Box(
                    modifier = Modifier
                        .clickable { onAddSet() }
                        .padding(4.dp)
                        .size(40.dp, 24.dp)
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
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseGroupItemPreview(
) {
    TapMyDayTheme {
        Surface {
            Column(Modifier.fillMaxSize()) {
                ExerciseGroupItem(
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
                    ),
                    isHistoryItem = false
                )
                ExerciseGroupItem(
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
                    ),
                    isHistoryItem = true
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ExerciseGroupItemPreviewLight() {
    ExerciseGroupItemPreview()
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExerciseGroupItemPreviewDark() {
    ExerciseGroupItemPreview()
}