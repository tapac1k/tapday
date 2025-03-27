package com.tapac1k.training.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.training.presentation.R
import com.tapac1k.training.contract.ExerciseSet

@Composable
fun ExerciseSetItem(
    withWeight: Boolean,
    timeBased: Boolean,
    exerciseSet: ExerciseSet,
    modifier: Modifier = Modifier
) {
    var completion = 0
    if (!withWeight || exerciseSet.weight != null) {
        completion++
    }
    if (timeBased && exerciseSet.time != null) {
        completion++
    }
    if (!timeBased && exerciseSet.reps != null) {
        completion++
    }

    Row(
        modifier
            .fillMaxHeight()
            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.75f), MaterialTheme.shapes.large).run {
                if (completion == 1) {
                    border(color = MaterialTheme.colorScheme.primary, width = 1.dp, shape = MaterialTheme.shapes.large)
                } else {
                    this
                }
            }
            .padding(horizontal = 8.dp)
        , verticalAlignment = Alignment.CenterVertically) {
        if (withWeight && exerciseSet.weight != null) {
            Icon(painterResource(R.drawable.ic_weight), contentDescription = null, Modifier.alpha(0.8f).padding(end = 4.dp).size(20.dp))
            Text("${exerciseSet.weight}", Modifier, style = MaterialTheme.typography.bodyMedium)
        }
        if (timeBased && exerciseSet.time != null) {
            if (withWeight && exerciseSet.weight != null) {
                VerticalDivider(Modifier.height(20.dp).padding(horizontal = 4.dp))
            }
            Icon(Icons.Filled.Timer, contentDescription = null, Modifier.padding(end = 4.dp).size(20.dp).alpha(0.8f))
            Text("${exerciseSet.time}", Modifier, style = MaterialTheme.typography.bodyMedium)
        }
        if (!timeBased && exerciseSet.reps != null) {
            if (withWeight && exerciseSet.weight != null) {
                VerticalDivider(Modifier.height(20.dp).padding(horizontal = 4.dp))
            }
            Icon(Icons.Filled.Repeat, contentDescription = null, Modifier.alpha(0.8f).padding(end = 4.dp).size(20.dp))
            Text("${exerciseSet.reps}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ExerciseSetItemPreview(
) {
    TapMyDayTheme {
        Surface {
            FlowRow(Modifier.fillMaxWidth()) {
                ExerciseSetItem(true, false, ExerciseSet("1", 1.24f, 1, 10), Modifier.height(30.dp).padding(4.dp))
                ExerciseSetItem(true, true, ExerciseSet("1", 1f, 1, 10), Modifier.height(30.dp).padding(4.dp))
                ExerciseSetItem(false, false, ExerciseSet("1", 1f, 1, 10), Modifier.height(30.dp).padding(4.dp))
                ExerciseSetItem(false, true, ExerciseSet("1", 1f, 1, 10), Modifier.height(30.dp).padding(4.dp))
                ExerciseSetItem(true, true, ExerciseSet("1", null, 1, 10), Modifier.height(30.dp).padding(4.dp))
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ExerciseSetItemPreviewLight() {
    ExerciseSetItemPreview()
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ExerciseSetItemPreviewDark() {
    ExerciseSetItemPreview()
}