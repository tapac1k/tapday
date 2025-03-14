package com.tapac1k.day_list.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.theme.moodColor
import com.tapac1k.compose.theme.sleepColor
import com.tapac1k.compose.theme.stateColor
import com.tapac1k.compose.widgets.CircularView
import com.tapac1k.day.contract.DayActivity

@Composable
fun ActivityView(modifier: Modifier = Modifier, dayActivity: DayActivity) {
    Box (modifier = modifier){
        CircularView(modifier = Modifier.fillMaxSize(), progress = dayActivity.sleepHours / 16f, strokeWidth = 2.dp, ringColor = sleepColor)
        CircularView(modifier = Modifier.fillMaxSize().padding(3.dp), progress = dayActivity.state / 100f, strokeWidth = 2.dp, ringColor = stateColor)
        CircularView(modifier = Modifier.fillMaxSize().padding(6.dp), progress = dayActivity.mood/ 100f, strokeWidth = 2.dp, ringColor = moodColor)
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ActivityViewPreviewLight() {
    ActivityViewPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActivityViewPreviewDark() {
    ActivityViewPreview()
}

@Composable
fun ActivityViewPreview() {
    TapMyDayTheme {
        Surface {
            ActivityView(Modifier.padding(8.dp).size(50.dp), DayActivity(2f, 75, 42))
        }
    }
}