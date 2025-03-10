package com.tapac1k.settings.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel()
) {
    SettingsScreenContent(
        onLogoutClick = viewModel::logout
    )
}

@Composable
fun SettingsScreenContent(
    onLogoutClick: () -> Unit = {},
) {
    Surface(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        Column {
            Spacer(Modifier.weight(1f))
            Button(onClick = onLogoutClick) {
                Text("Logout")
            }
        }
    }

}

@Preview
@Composable
fun SettingsScreenPreview() {
    TapMyDayTheme {
        SettingsScreenContent()
    }
}