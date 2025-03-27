package com.tapac1k.training.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Luggage
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.alpha
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.training.contract.Exercise
import com.tapac1k.training.contract.ShortTrainingInfo
import com.tapac1k.training.contract.TrainingInfo
import com.tapac1k.training.contract.TrainingTag

val dateFormat = java.text.SimpleDateFormat("EEEE")
val dateFullFormat = java.text.SimpleDateFormat("dd MMM yyyy")

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TrainingItem(
    trainingInfo: ShortTrainingInfo,
    modifier: Modifier = Modifier,
    onTrainingClick: (ShortTrainingInfo) -> Unit = {},
    onTagClick: (TrainingTag) -> Unit = {}
) {
    Column(
        modifier
            .padding(8.dp)
            .clickable { onTrainingClick(trainingInfo) }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(0.2f), shape = RoundedCornerShape(6.dp)
            )
            .border(1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(0.7f), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {

        Row(Modifier
            .padding(4.dp)
        ) {
            Column(
                modifier =
                Modifier.weight(1f)
            ) {
                Text(dateFormat.format(trainingInfo.date), Modifier,style = MaterialTheme.typography.headlineMedium)
                Text(dateFullFormat.format(trainingInfo.date), Modifier,style = MaterialTheme.typography.labelSmall)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text("exercises: ${trainingInfo.exerciseCount}", Modifier,style = MaterialTheme.typography.labelSmall.copy(textDecoration = TextDecoration.Underline))
                Text("sets: ${trainingInfo.totalSets}", Modifier,style = MaterialTheme.typography.labelSmall.copy(textDecoration = TextDecoration.Underline))
            }
        }
        FlowRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            trainingInfo.tags.forEach { tag ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { onTagClick(tag) }
                        .wrapContentSize()
                        .background(Color(tag.color).copy(alpha = 0.5f), RoundedCornerShape(percent = 100))
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                ) {
                    Text(tag.value, maxLines = 1, style = MaterialTheme.typography.labelSmall)
                }
            }
        }
    }
}

@Composable
fun TrainingItemPreview() {
    TapMyDayTheme {
        Surface(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxWidth()) {
                TrainingItem(
                    ShortTrainingInfo(
                        date = System.currentTimeMillis(),
                        desc = "",
                        id = "5",
                        exerciseInfo = emptyList(),
                    ),
                    Modifier.fillMaxWidth()
                )
                TrainingItem(
                    ShortTrainingInfo(
                        date = System.currentTimeMillis(),
                        desc = "",
                        id = "5",
                        exerciseInfo = emptyList(),
                    ),
                )
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, widthDp = 320)
@Composable
fun TrainingItemPreviewLight() {
    TrainingItemPreview()
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 320)
@Composable
fun TrainingItemPreviewDark() {
    TrainingItemPreview()
}