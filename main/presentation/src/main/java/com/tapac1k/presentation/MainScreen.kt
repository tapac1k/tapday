package com.tapac1k.presentation

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
import androidx.core.provider.FontsContractCompat.Columns
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    onLoggedOut: () -> Unit,
) {
    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                if (event is MainEvent.LoggedOut) {
                    onLoggedOut()
                }
            }
        }
        MainScreenContent(
            onLogoutClick = viewModel::logout,
        )
    }
}

@Composable
fun MainScreenContent(
    onSettingClick: () -> Unit = {},
    onLogoutClick: () -> Unit = {},
) {
    Column {
        Button(onClick = onSettingClick) {
            Text("Settings")
        }
        Button(onClick = onLogoutClick) {
            Text("Logout")
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