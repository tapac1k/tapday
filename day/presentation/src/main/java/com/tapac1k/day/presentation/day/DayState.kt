package com.tapac1k.day.presentation.day

import androidx.compose.ui.text.input.TextFieldValue
import com.tapac1k.day.contract.DayActivity

data class DayState(
    val dayActivity: DayActivity = DayActivity(),
    val description: TextFieldValue = TextFieldValue(""),
    val loading: Boolean = true,
)
