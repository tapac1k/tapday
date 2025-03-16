package com.tapac1k.training.presentation.tag

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.utils.common.WithBackNavigation
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TagDialogScreen(
    viewModel: TrainingTagDialogViewModel = viewModel(),
    navigation: WithBackNavigation,
) {
    val text by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    LaunchedEffect("events") {
        viewModel.events.collectLatest {
            when(it) {
                is TrainingTagEvent.ShowMessage -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                TrainingTagEvent.Dismiss -> navigation.onBack()
            }
        }
    }
    TagDialogScreenContent(
        text = text,
        onTextChanged = viewModel::updateText,
        onSave = viewModel::saveTag,
        onDismiss = navigation::onBack
    )
}

@Composable
fun TagDialogScreenContent(
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    onSave: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    Dialog(onDismissRequest = onDismiss) {
        Column {
            TextField(text, onTextChanged, placeholder = {
                Text("Tag name")
            })
            Button(onClick = onSave, modifier = Modifier.align(Alignment.End)) {
                Text("Save")
            }
        }
    }

}

@Composable
fun TagDialogScreenPreview() {
    TapMyDayTheme {
        TagDialogScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TagDialogScreenPreviewLight() {
    TagDialogScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TagDialogScreenPreviewDark() {
    TagDialogScreenPreview()
}


