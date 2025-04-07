package com.tapac1k.training.presentation.tags

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBarWithSearch
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.contract_ui.TrainingTagNavigation
import com.tapac1k.training.presentation.widget.TrainingTagItem

@Composable
fun TrainingTagsScreen(
    viewModel: TrainingTagsViewModel = viewModel(),
    navigation: TrainingTagNavigation,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    TrainingTagsScreenContent(
        state = state,
        updater = viewModel::updateState,
        navigation = navigation
    )
}

@Composable
fun TrainingTagsScreenContent(
    state: TrainingTagsState,
    updater: (TrainingTagsStateUpdate) -> Unit = {},
    navigation: TrainingTagNavigation? = null,
) {
    Scaffold(
        topBar = {
            TopBarWithSearch(
                query = state.query,
                onQueryChange = { updater(TrainingTagsStateUpdate.UpdateQuery(it)) },
                onBack = { navigation?.onBack() },
                onClear = { updater(TrainingTagsStateUpdate.ClearQuery) },
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = { navigation?.createTag() }
            ) {
                Text("Create tag", modifier = Modifier.padding(10.dp))
            }
        }) { padding ->
        LazyColumn(contentPadding = padding) {
            val tags = state.filteredTags
            items(tags.count(), key = { tags[it].id }) { index ->
                TrainingTagItem(
                    tags[index],
                    onClick = { navigation?.ediTag(tags[index]) }
                )
            }
        }
    }
}


@Composable
fun TrainingTagsPreview() {
    TapMyDayTheme {
        Box {
            TrainingTagsScreenContent(
                TrainingTagsState(
                    tags = listOf(
                        TrainingTag(
                            id = "1",
                            value = "Tag 1",
                        ),
                        TrainingTag(
                            id = "2",
                            value = "Tag 2",
                        ),
                        TrainingTag(
                            id = "3",
                            value = "Tag 3",
                        ),
                    ),
                    query = TextFieldValue("12")
                )
            )
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun TrainingTagsPreviewLight() {
    TrainingTagsPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TrainingTagsPreviewDark() {
    TrainingTagsPreview()
}