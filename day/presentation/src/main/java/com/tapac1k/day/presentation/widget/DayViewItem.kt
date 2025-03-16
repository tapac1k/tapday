package com.tapac1k.day.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.contract.getFormattedDate
import com.tapac1k.day.contract.getWeek

@Composable
fun DayViewItem(
    modifier: Modifier,
    dayInfo: DayInfo,
    onClick: () -> Unit = {}
) {
    val active = dayInfo.updated != null
    Row(
        modifier
            .clickable { onClick() }
            .background(color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = if (active) 0.2f else 0.1f), shape = RoundedCornerShape(6.dp))
            .border(1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = if (active) 0.7f else 0.4f), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(Modifier.weight(1f).alpha(if (active) 1f else 0.4f)) {
            Text(dayInfo.getWeek(), style = MaterialTheme.typography.headlineMedium)
            Text(dayInfo.getFormattedDate(), style = MaterialTheme.typography.labelSmall)
        }
        if (!active) {
            Icon(
                Icons.Rounded.Add, "add",
            )
        } else {
            ActivityView(Modifier.size(40.dp), dayActivity = dayInfo.dayActivity)
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DayViewItemLight() {
    DayViewItemPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DayViewItemPreviewDark() {
    DayViewItemPreview()
}

@Composable
fun DayViewItemPreview() {
    TapMyDayTheme {
        Surface {

        Column(
            Modifier
                .height(320.dp)
                .width(300.dp)
        ) {
            DayViewItem(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), DayInfo(140, DayActivity(21f, 75, 42), 20)
            )

            DayViewItem(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp), DayInfo(140, DayActivity(2f, 75, 42), null)
            )
        }

        }
    }
}