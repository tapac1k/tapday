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
import com.tapac1k.training.contract.ExerciseSet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupSetDialog(
    set: ExerciseSet?,
    withWeight: Boolean,
    timeBased: Boolean,
    onDismiss: () -> Unit,
    onSave: (id: String?, weight: Float?, reps: Int?, time: Int?) -> Unit,
) {
    var currentTime by remember { mutableStateOf(set?.time?.toString().orEmpty()) }
    var currentWeight by remember { mutableStateOf(set?.weight?.toString().orEmpty()) }
    var currentReps by remember { mutableStateOf(set?.reps?.toString().orEmpty()) }

    val onSaveClick: () -> Unit = {
        onSave(
            set?.id,
            if (withWeight) currentWeight.toFloatOrNull() else null,
            currentReps.toIntOrNull(),
            if (timeBased) currentTime.toIntOrNull() else null
        )
    }
    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                )
        ) {
            if (withWeight) {
                TextField(
                    currentWeight, { currentWeight = it },
                    Modifier.fillMaxWidth().padding(8.dp),
                    placeholder = {
                        Text("Weight")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                )
            }
            if (timeBased) {
                TextField(
                    currentTime, { currentTime = it },
                    Modifier.fillMaxWidth().padding(8.dp),
                    placeholder = {
                        Text("Time")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {onSaveClick()})
                )
            } else {
                TextField(
                    currentReps, { currentReps = it },
                    Modifier.fillMaxWidth().padding(8.dp),
                    placeholder = {
                        Text("Reps")
                    },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = {onSaveClick()})
                )
            }
            Button(onClick = onSaveClick, modifier = Modifier.padding(4.dp).align(Alignment.End)) {
                Text("Save")
            }
        }
    }
}