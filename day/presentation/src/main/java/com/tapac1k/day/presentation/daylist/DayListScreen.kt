package com.tapac1k.day.presentation.daylist

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract.DayInfo
import com.tapac1k.day.contract_ui.DayListNavigation
import com.tapac1k.day.presentation.widget.DayViewItem
import kotlinx.coroutines.flow.flowOf

@Composable
fun DayListScreen(
    viewModel: DayListViewModel = viewModel(),
    dayListNavigation: DayListNavigation
) {
    val dayListData = viewModel.dayFlow.collectAsLazyPagingItems()
    val lifecycleOwner = LocalLifecycleOwner.current
    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
            dayListData.refresh()
        }
    }

    DayListContent(
        pagingData = dayListData,
        openCurrentDayClick = {
            dayListNavigation.openDayDetails(viewModel.getCurrentDay())
        },
        openDay = dayListNavigation::openDayDetails
    )
}

@Composable
fun DayListContent(
    pagingData: LazyPagingItems<DayInfo>,
    openCurrentDayClick: () -> Unit = {},
    openDay: (Long) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .windowInsetsTopHeight(
                        WindowInsets.systemBars.only(WindowInsetsSides.Top)
                    )
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(vertical = 16.dp)
                    .fillMaxWidth(),
                onClick = openCurrentDayClick
            ) {
                Text("Make today great again", modifier = Modifier.padding(10.dp))
            }
        }
    ) { paddingValues ->
        LazyColumn(reverseLayout = true, contentPadding = paddingValues) {
            items(pagingData.itemCount, key = { pagingData[it]!!.id }) {
                val data = pagingData[it]!!
                DayViewItem(
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
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
                        DayInfo(id = 42, dayActivity = DayActivity(), updated = 125, "Title"),
                        DayInfo(id = 32, dayActivity = DayActivity(), updated = 125,"Title")
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
                        DayInfo(id = 42, dayActivity = DayActivity(), updated = 125,"Title"),
                        DayInfo(id = 32, dayActivity = DayActivity(), updated = 125,"Title")
                    )
                )
            ).collectAsLazyPagingItems()
        )
    }
}