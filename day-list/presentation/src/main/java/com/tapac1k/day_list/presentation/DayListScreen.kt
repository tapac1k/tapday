package com.tapac1k.day_list.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme

@Composable
fun DayListScreen(
    viewModel: DayListViewModel = viewModel(),
    onSettingClick: () -> Unit,
) {
    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {

        MainScreenContent(
            onSettingClick = onSettingClick
        )
    }
}

@Composable
fun MainScreenContent(
    onSettingClick: () -> Unit = {},
) {
    Column {
        Button(onClick = onSettingClick) {
            Text("Settings")
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    TapMyDayTheme {
        MainScreenContent()
    }
}