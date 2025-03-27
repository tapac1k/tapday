package com.tapac1k.training.presentation.training_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetDescriptionDialog(
    text: String = "",
    onSave: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var description by remember { mutableStateOf(text) }
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
                description, { description = it },
                Modifier.fillMaxWidth(),
                placeholder = {
                    Text("Description")
                },
            )
            Button(
                onClick = {
                    onSave(description)
                    onDismiss()
                }, enabled = description.trim().length > 2, modifier = Modifier
                    .padding(4.dp)
                    .align(Alignment.End)
            ) {
                Text("Save")
            }
        }
    }

}

