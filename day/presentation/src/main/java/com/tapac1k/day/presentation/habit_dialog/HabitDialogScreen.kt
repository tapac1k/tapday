package com.tapac1k.day.presentation.habit_dialog

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.utils.common.WithBackNavigation
import kotlinx.coroutines.flow.collectLatest


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun HabitDialogScreen(
    viewModel: HabitDialogViewModel = viewModel(),
    navigation: WithBackNavigation,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect("events") {
        viewModel.events.collectLatest {
            when (it) {
                is HabitDialogEvent.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                HabitDialogEvent.Dismiss -> navigation.onBack()
            }
        }
    }

    ModalBottomSheet(onDismissRequest = navigation::onBack) {
        HabitDialogScreenContent(
            state = state,
            updater = viewModel::updateState,
            onSave = viewModel::saveHabit,
        )
    }
}

@Composable
private fun HabitDialogScreenContent(
    state: HabitDialogState,
    updater: (HabitDialogUpdate) -> Unit = {},
    onSave: () -> Unit = {},
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
            )
    ) {
        TextField(
            state.name, { updater.invoke(HabitDialogUpdate.Name(it)) },
            Modifier.fillMaxWidth(),
            placeholder = {
                Text("Habit name")
            },
            keyboardActions = KeyboardActions(onDone = { onSave() }),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(state.isPositive, onCheckedChange = { updater(HabitDialogUpdate.IsPositive(it)) })
            Text("is positive habit", Modifier.weight(1f))
            Button(
                onClick = onSave, enabled = state.name.text.trim().length > 2, modifier = Modifier
                    .padding(4.dp)
            ) {
                Text("Save")
            }
        }
    }
}

@Composable
private fun HabitDialogScreenPreview() {
    TapMyDayTheme {
        Surface(Modifier.fillMaxSize()) {
            HabitDialogScreenContent(
                HabitDialogState(
                    name = TextFieldValue("Habit name"),
                    isPositive = true,
                ), updater = {}, onSave = {})
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HabitDialogScreenPreviewLight() {
    HabitDialogScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun HabitDialogScreenPreviewDark() {
    HabitDialogScreenPreview()
}

