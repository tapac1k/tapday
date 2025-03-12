package com.tapac1k.day.presentation.widget

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.getFormatSleepHours
import java.util.Locale
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sqrt

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    progress: Float, // Current progress (0f to 1f)
    onProgressChange: (Float) -> Unit, // Callback when user adjusts
    ringColor: Color = Color.Red, // Default color
    strokeWidth: Dp = 20.dp, // Thickness of the ring
    startAngle: Float = -90f // Start position of the ring (default: top)
) {
    var touched by remember { mutableStateOf(false) }
    var animate by remember { mutableStateOf(true) }
    var center by remember { mutableStateOf(Offset.Zero) }
    var radius by remember { mutableStateOf(0f) }
    val currentProgress = if (!animate) {
        progress
    } else {
       val animated by animateFloatAsState(
               targetValue = progress,
               animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing),
               label = "Slider Animation"
        )
        animated
    }
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    animate = false
                    onProgressChange(getProgress(center, change.position))
                }
            }
            .pointerInteropFilter {
                when (it.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        val distance = sqrt(
                            (it.x - center.x).pow(2) + (it.y - center.y).pow(2)
                        )
                        touched =  distance < radius + strokeWidth.value
                        if (touched) {
                            animate = true
                            onProgressChange(getProgress(center, Offset(it.x, it.y)))
                        }
                    }
                }
                return@pointerInteropFilter touched

            }
    ) {
        center = size.center
        radius = size.minDimension / 2f - strokeWidth.toPx() / 2
        // Draw background ring (light gray)
        drawArc(
            color = ringColor.copy(alpha = 0.2f),
            startAngle = 0f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )

        // Draw progress ring
        drawArc(
            color = ringColor,
            startAngle = startAngle,
            sweepAngle =  currentProgress * 360f,
            useCenter = false,
            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(center.x - radius, center.y - radius)
        )
    }
}

// Helper function to calculate angle based on touch position
private fun calculateAngle(center: Offset, touch: Offset): Float {
    val angle = atan2(touch.y - center.y, touch.x - center.x) * (180f / Math.PI.toFloat()) + 90f
    return (angle + 360) % 360 // Normalize angle to 0-360 degrees
}

private fun getProgress(center: Offset, offset: Offset): Float {
    return (calculateAngle(center, offset) / 360f).coerceIn(0f, 1f)
}

@Composable
fun ActivityRings(
    dayActivity: DayActivity,
    onMoodUpdate: (Int) -> Unit,
    onStateUpdate: (Int) -> Unit,
    onSleepUpdate: (Float) -> Unit
) {
    val moodProgress = dayActivity.mood / 100f
    val sleepProgresss = dayActivity.sleepHours / 16f
    val stateProgress = dayActivity.state / 100f
    Box(
        Modifier
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
    Box(modifier.fillMaxSize().aspectRatio(1f).padding(bottom = 8.dp), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Row(Modifier.offset(y = 20.dp)) {
                AcitivtyCircle(
                    Modifier.offset(x = 5.dp).background(color = Color(MoodColor).copy(alpha = 0.3f), shape = CircleShape, ),
                    dayActivity.mood.toString(), "Mood"
                )
                AcitivtyCircle(
                    Modifier.offset(x = -5.dp).background(color = Color(StateColor).copy(alpha = 0.3f), shape = CircleShape, ),
                    dayActivity.state.toString(), "State"
                )
            }
            AcitivtyCircle(
                Modifier.background(color = Color(SleepColor).copy(alpha = 0.3f), shape = CircleShape, ),
                dayActivity.getFormatSleepHours(), "Sleep"
            )
        }

    }
}

@Composable
fun AcitivtyCircle(modifier: Modifier = Modifier,value: String, title: String) {
    Column(modifier
        .size(75.dp)
        .border(
            1.dp,
            color = MaterialTheme.colorScheme.outlineVariant,
            shape = CircleShape
        ), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
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

private const val SleepColor = 0xFFECCE0C
private const val StateColor = 0xFF0C9E01
private const val MoodColor = 0xFF0C9EC1