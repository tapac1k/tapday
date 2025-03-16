package com.tapac1k.training.contract

import androidx.core.graphics.ColorUtils
import kotlin.math.absoluteValue

data class TrainingTag(
    val id: String,
    val value: String,
) {

    val color: Int get()  {
            val hue = (value.hashCode() % 360).absoluteValue
            return ColorUtils.HSLToColor(floatArrayOf(hue.toFloat(), 0.3f, 0.5f))
        }
}
