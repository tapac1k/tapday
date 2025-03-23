package com.tapac1k.training.presentation.training_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.training.contract_ui.TrainingListNavigation

@Composable
fun TrainingListScreen(
    viewModel: TrainingListViewModel = viewModel(),
    navigation: TrainingListNavigation,
    ) {
    TrainingListScreenContent(
        onOpen = navigation::openTraining,
        onCreate = navigation::createTraining

    )
}

@Composable
fun TrainingListScreenContent(
    onCreate: ()-> Unit = {},
    onOpen: (String)->Unit = {}
) {
    Scaffold(
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = { onCreate() }
            ) {
                Text("Start training", modifier = Modifier.padding(10.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) { }
    }
}

@Composable
fun TrainingListScreenPreview() {
    TapMyDayTheme {
        TrainingListScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TrainingListScreenPreviewLight() {
    TrainingListScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TrainingListScreenPreviewDark() {
    TrainingListScreenPreview()
}
