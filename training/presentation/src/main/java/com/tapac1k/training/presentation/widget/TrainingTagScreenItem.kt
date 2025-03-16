package com.tapac1k.training.presentation.widget

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.training.contract.TrainingTag

@Composable
fun TrainingTagItem(
    tag: TrainingTag,
    onExercisesClick: ((TrainingTag) -> Unit)? = null,
    onTrainingsClick: ((TrainingTag) -> Unit)? = null,
    onClick: ((TrainingTag) -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(4.dp)
            .clickable(enabled = onClick != null) { onClick?.invoke(tag) }
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer.copy(0.2f), shape = RoundedCornerShape(6.dp)
            )
            .border(1.dp, color = MaterialTheme.colorScheme.outlineVariant.copy(0.7f), shape = RoundedCornerShape(6.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Column(Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = tag.value.capitalize(Locale.current), style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.padding(4.dp).size(10.dp).background(Color(tag.color), shape = CircleShape))
            }

            Row {
                Text("75 execisises", Modifier.padding(4.dp), style = MaterialTheme.typography.labelSmall.copy(textDecoration = TextDecoration.Underline))
                Text("34 trainings", Modifier.padding(4.dp), style = MaterialTheme.typography.labelSmall.copy(textDecoration = TextDecoration.Underline))
            }
        }
        Icon(Icons.Filled.Edit, contentDescription = "edit", modifier = Modifier.padding(4.dp))
    }
}


@Composable
private fun TrainingTagItemPreview() {
    TapMyDayTheme {

        Surface {

            Column(Modifier.fillMaxSize()) {

                TrainingTagItem(
                    modifier = Modifier.fillMaxWidth(),
                    tag = TrainingTag("1", "Середня дельта"),
                    onClick = {},
                )

                TrainingTagItem(
                    modifier = Modifier.fillMaxWidth(),
                    tag = TrainingTag("1", "Біцепс"),
                    onClick = {},
                )

                TrainingTagItem(
                    modifier = Modifier.fillMaxWidth(),
                    tag = TrainingTag("1", "Груди"),
                    onClick = {},
                )
            }
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TrainingTagItemPreviewLight() {
    TrainingTagItemPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TrainingTagItemPreviewDark() {
    TrainingTagItemPreview()
}