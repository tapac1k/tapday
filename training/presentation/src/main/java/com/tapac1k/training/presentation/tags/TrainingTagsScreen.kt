package com.tapac1k.training.presentation.tags

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrainingTagsScreenContent(
    state: TrainingTagsState,
    updater: (TrainingTagsStateUpdate) -> Unit = {},
    navigation: TrainingTagNavigation? = null,
) {
    Scaffold(
        topBar = {
            val showSearch = state.query != null
            Column {
                TopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .zIndex(12f)
                ) {
                    IconButton(onClick = { navigation?.onBack() }) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
                    }
                    Spacer(Modifier.weight(1f))
                    if (state.query == null) {
                        IconButton(onClick = { updater.invoke(TrainingTagsStateUpdate.UpdateQuery("")) }) {
                            Icon(Icons.Filled.Search, "Search")
                        }
                    }
                }
                AnimatedVisibility(
                    modifier = Modifier
                        .offset(y = -16.dp)
                        .zIndex(1f), visible = state.query != null
                ) {

                    SearchBarDefaults.InputField(
                        state.query.orEmpty(),
                        onQueryChange = { updater.invoke(TrainingTagsStateUpdate.UpdateQuery(it)) },
                        onSearch = { updater.invoke(TrainingTagsStateUpdate.UpdateQuery(it)) },
                        expanded = true,
                        placeholder = { Text("Search", Modifier.alpha(.5f)) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                            )
                            .padding(top = 16.dp),
                        onExpandedChange = {},
                        trailingIcon = {
                            IconButton(onClick = { updater.invoke(TrainingTagsStateUpdate.ClearQuery) }) {
                                Icon(Icons.Filled.Close, "")
                            }
                        }
                    )
                }
            }
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
                    query = "12"
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