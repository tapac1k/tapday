package com.tapac1k.compose.widgets

import android.content.res.Configuration
import android.view.MotionEvent
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import kotlin.math.atan2
import kotlin.math.pow
import kotlin.math.sqrt

@Composable
fun CircularView(
    modifier: Modifier = Modifier,
    progress: Float,
    ringColor: Color = Color.Red,
    strokeWidth: Dp = 20.dp,
    startAngle: Float = -90f
) {
    var center by remember { mutableStateOf(Offset.Zero) }
    var radius by remember { mutableStateOf(0f) }
    Canvas(
        modifier = modifier
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


@Preview(heightDp = 320)
@Composable
fun ActivityRingsScreenPreview() {
    TapMyDayTheme {
        Column(modifier = Modifier.fillMaxSize( )) {
            CircularSlider(Modifier.fillMaxWidth(), 0.5f)
            CircularView(Modifier.size(40.dp), 0.5f)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, heightDp = 320)
@Composable
fun ActivityRingsScreenPreviewDark() {
    TapMyDayTheme {
        Surface(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxSize()) {
                CircularSlider(Modifier.weight(1f).aspectRatio(1f), 0.5f)
                CircularView(Modifier.size(40.dp), 0.5f, strokeWidth = 4.dp)
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CircularSlider(
    modifier: Modifier = Modifier,
    progress: Float = 0f,
    onProgressChange: (Float) -> Unit = {},
    ringColor: Color = Color.Red,
    strokeWidth: Dp = 20.dp,
    startAngle: Float = -90f
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
                        touched = distance < radius + strokeWidth.value
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
            sweepAngle = currentProgress * 360f,
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
