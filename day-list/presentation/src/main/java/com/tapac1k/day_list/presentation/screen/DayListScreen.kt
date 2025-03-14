package com.tapac1k.day_list.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day_list.contract_ui.DayListNavigation
import com.tapac1k.day_list.presentation.widget.DayViewItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun DayListScreen(
    viewModel: DayListViewModel = viewModel(),
    dayListNavigation: DayListNavigation
) {
    val dayListData = viewModel.dayFlow.collectAsLazyPagingItems()
    DayListContent(
        pagingData = dayListData,
        onSettingClick = dayListNavigation::openSettings,
        openCurrentDayClick = {
            dayListNavigation.openDayDetails(viewModel.getCurrentDay())
        },
        openDay = dayListNavigation::openDayDetails
    )
}

@Composable
fun DayListContent(
    pagingData: LazyPagingItems<DayInfo>,
    onSettingClick: () -> Unit = {},
    openCurrentDayClick: () -> Unit = {},
    openDay: (Long) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopBar(Icons.Rounded.Settings to onSettingClick)
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth()
                    .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom)),
                onClick = openCurrentDayClick
            ) {
                Text("Make today great again", modifier = Modifier.padding(10.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(reverseLayout = true, contentPadding = paddingValues) {
            items (pagingData.itemCount, key = {pagingData[it]!!.id}){
                val data = pagingData[it]!!
                DayViewItem(
                    Modifier.fillMaxWidth().padding(8.dp),
                    dayInfo = data,
                    onClick = { openDay(data.id) }
                )
            }
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    TapMyDayTheme {
        DayListContent(
            flowOf(
                PagingData.from(
                    listOf(
                        DayInfo(id = 42, dayActivity = DayActivity(), updated = 125),
                        DayInfo(id = 32, dayActivity = DayActivity(), updated = 125)
                    )
                )
            ).collectAsLazyPagingItems()
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreviewDark() {
    TapMyDayTheme {
        DayListContent(
            flowOf(
                PagingData.from(
                    listOf(
                        DayInfo(id = 42, dayActivity = DayActivity(), updated = 125),
                        DayInfo(id = 32, dayActivity = DayActivity(), updated = 125)
                    )
                )
            ).collectAsLazyPagingItems())
    }
}