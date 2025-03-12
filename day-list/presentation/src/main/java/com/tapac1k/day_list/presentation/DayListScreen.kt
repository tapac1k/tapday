package com.tapac1k.day_list.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.day_list.contract_ui.DayListNavigation

@Composable
fun DayListScreen(
    viewModel: DayListViewModel = viewModel(),
    dayListNavigation: DayListNavigation
) {
    DayListContent(
        onSettingClick = dayListNavigation::openSettings,
        openCurrentDayClick = {
            dayListNavigation.openDayDetails(viewModel.getCurrentDay())
        },
    )
}

@Composable
fun DayListContent(
    onSettingClick: () -> Unit = {},
    openCurrentDayClick: () -> Unit = {},
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            TopBar(Icons.Rounded.Settings to onSettingClick)
            LazyColumn {

            }

        }
        Button (
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .align(Alignment.BottomCenter),
            onClick = openCurrentDayClick
        ) {
            Text("Make today great again", modifier = Modifier.padding(10.dp))
        }

    }


}

@Preview
@Composable
fun MainScreenPreview() {
    TapMyDayTheme {
        DayListContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreviewDark() {
    TapMyDayTheme {
        DayListContent()
    }
}