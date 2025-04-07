package com.tapac1k.training.presentation.training_list

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.training.contract.ShortTrainingInfo
import com.tapac1k.training.contract_ui.TrainingListNavigation
import com.tapac1k.training.presentation.widget.TrainingItem
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

@Composable
fun TrainingListScreen(
    viewModel: TrainingListViewModel = viewModel(),
    navigation: TrainingListNavigation,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val trainingListData = viewModel.trainingsFlow.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            trainingListData.refresh()
        }
    }
    TrainingListScreenContent(
        trainingListData,
        onOpen = navigation::openTraining,
        onCreate = navigation::createTraining

    )
}

@Composable
fun TrainingListScreenContent(
    trainingsListData: LazyPagingItems<ShortTrainingInfo>,
    onCreate: () -> Unit = {},
    onOpen: (String) -> Unit = {}
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
        LazyColumn(
            contentPadding = paddingValues,
            reverseLayout = true,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(trainingsListData.itemCount, {
                trainingsListData.get(it)!!.id
            }) { index ->
                val training = trainingsListData[index]
                if (training != null) {
                    TrainingItem(
                        training,
                        onTrainingClick = { onOpen(it.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun TrainingListScreenPreview() {
    TapMyDayTheme {
        TrainingListScreenContent(flow {
            emit(
                PagingData.from(
                    listOf(
                        ShortTrainingInfo(id = "11", "2", 12, emptyList()),
                        ShortTrainingInfo(id = "11", "2", 12, emptyList()),
                    )
                )
            )
        }.collectAsLazyPagingItems())
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
