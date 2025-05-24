package com.tapac1k.day.presentation.widget

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.theme.moodColor
import com.tapac1k.compose.theme.sleepColor
import com.tapac1k.compose.theme.stateColor
import com.tapac1k.compose.widgets.CircularSlider
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.getFormatSleepHours
import kotlinx.coroutines.delay
import kotlin.math.roundToInt


@Composable
fun ActivityRings(
    dayActivity: DayActivity,
    modifier: Modifier = Modifier,
    onMoodUpdate: (Int) -> Unit,
    onStateUpdate: (Int) -> Unit,
    onSleepUpdate: (Float) -> Unit
) {
    val moodProgress = dayActivity.mood / 100f
    val sleepProgresss = dayActivity.sleepHours / 16f
    val stateProgress = dayActivity.state / 100f
    Box(
        modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularSlider(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f),
                strokeWidth = 20.dp,
                progress = sleepProgresss,
                onProgressChange = {
                    onSleepUpdate(it * 16)
                },
                ringColor = Color(0xFFECCE0C.toInt())
            )
            CircularSlider(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                strokeWidth = 20.dp,
                progress = stateProgress,
                onProgressChange = {
                    onStateUpdate((it * 100).roundToInt())
                },
                ringColor = Color(0xFF0C9E01.toInt())
            )
            CircularSlider(
                modifier = Modifier
                    .padding(50.dp)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                strokeWidth = 20.dp,
                progress = moodProgress,
                onProgressChange = {
                    onMoodUpdate((it * 100).roundToInt())
                },
                ringColor = Color(0xFF0C9EC1.toInt())
            )

            ActivityArcCircleContent(dayActivity, Modifier.padding(70.dp))
        }
    }
}

@Composable
fun ActivityArcCircleContent(dayActivity: DayActivity = DayActivity(), modifier: Modifier) {
    Box(
        modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .padding(bottom = 8.dp), contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Row(Modifier.offset(y = 20.dp)) {
                AcitivtyCircle(
                    color = moodColor,
                    value = dayActivity.mood.toString(),
                    title = "Mood"
                )
                AcitivtyCircle(
                    color = stateColor,
                    value = dayActivity.state.toString(),
                    title = "State"
                )
            }
            AcitivtyCircle(
                color = sleepColor,
                value = dayActivity.getFormatSleepHours(),
                title = "Sleep",
            )
        }

    }
}

@Composable
fun AcitivtyCircle(modifier: Modifier = Modifier, color: Color = Color.Red, value: String, title: String) {
    var isHighlighted by remember { mutableStateOf(true) }

    // Animate background alpha (1f when updated, fades to 0f)
    val backgroundAlpha by animateFloatAsState(
        targetValue = if (isHighlighted) 0.5f else 0.3f,
        animationSpec = tween(500), // 1 sec fade-out
        label = "Background Alpha Animation"
    )

    // Trigger highlight on text change
    LaunchedEffect(value) {
        isHighlighted = true
        delay(500)
        isHighlighted = false // Start fade-out animation
    }
    Column(
        modifier
            .size(75.dp)
            .background(color = color.copy(alpha = backgroundAlpha), shape = CircleShape)
            .border(
                1.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = CircleShape
            ), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.height(10.dp))
        Text(value, style = MaterialTheme.typography.labelLarge, fontSize = TextUnit(20f, TextUnitType.Sp))
        Text(
            title.lowercase(),
            modifier = Modifier, fontWeight = FontWeight.Thin,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview
@Composable
fun ActivityRingsScreenPreview() {
    TapMyDayTheme {
        Surface {
            var dayActivity by remember { mutableStateOf(DayActivity(1f, 30, 50)) }
            ActivityRings(
                dayActivity,
                onMoodUpdate = { dayActivity = dayActivity.copy(mood = it) },
                onStateUpdate = { dayActivity = dayActivity.copy(state = it) },
                onSleepUpdate = { dayActivity = dayActivity.copy(sleepHours = it) },
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActivityRingsScreenPreviewDark() {
    TapMyDayTheme {
        Surface {
            var dayActivity by remember { mutableStateOf(DayActivity(1f, 30, 50)) }
            ActivityRings(
                dayActivity,
                onMoodUpdate = { dayActivity = dayActivity.copy(mood = it) },
                onStateUpdate = { dayActivity = dayActivity.copy(state = it) },
                onSleepUpdate = { dayActivity = dayActivity.copy(sleepHours = it) },
            )
        }
    }
}
