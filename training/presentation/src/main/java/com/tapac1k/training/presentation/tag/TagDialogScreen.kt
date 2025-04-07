package com.tapac1k.training.presentation.tag

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            when (it) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TagDialogScreenContent(
    text: String = "",
    onTextChanged: (String) -> Unit = {},
    onSave: () -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                )
        ) {
            TextField(
                text, onTextChanged,
                Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Tag name")
                },
                keyboardActions = KeyboardActions(onDone = { onSave() }),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(onClick = onSave, enabled = text.trim().length > 2, modifier = Modifier.padding(4.dp).align(Alignment.End)) {
                Text("Save")
            }
        }
    }

}

@Composable
private fun TagDialogScreenPreview() {
    TapMyDayTheme {
        Surface(Modifier.fillMaxSize()) {
            TagDialogScreenContent()
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun TagDialogScreenPreviewLight() {
    TagDialogScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun TagDialogScreenPreviewDark() {
    TagDialogScreenPreview()
}


