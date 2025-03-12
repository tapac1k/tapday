package com.tapac1k.day.presentation.widget

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.center
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toOffset
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.contract.DayActivity
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
    var center by remember { mutableStateOf(Offset.Zero) }
    var radius by remember { mutableStateOf(0f) }
    Canvas(
        modifier = modifier
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    onProgressChange(getProgress(offset))
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, _ ->
                    onProgressChange(getProgress(change.position))
                }
            }
            .pointerInteropFilter {
                when (it.actionMasked) {
                    MotionEvent.ACTION_DOWN -> {
                        val distance = sqrt(
                            (it.x - center.x).pow(2) + (it.y - center.y).pow(2)
                        )
                        return@pointerInteropFilter distance < radius + strokeWidth.value / 2
                    }

                    else -> false
                }

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
            sweepAngle = progress * 360f,
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

private fun PointerInputScope.getProgress(offset: Offset): Float {
    return (calculateAngle(size.center.toOffset(), offset) / 360f).coerceIn(0f, 1f)
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
                .fillMaxWidth()
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
        }
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