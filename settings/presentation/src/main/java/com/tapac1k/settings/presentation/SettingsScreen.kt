package com.tapac1k.settings.presentation

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.settings.contract_ui.SettingProvider
import com.tapac1k.settings.contract_ui.SettingsNavigation

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = viewModel(),
    settingsNavigation: SettingsNavigation,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SettingsScreenContent(
        settingsNavigation = settingsNavigation,
        state = state,
        onLogoutClick = viewModel::logout,
    )
}

@Composable
fun SettingsScreenContent(
    settingsNavigation: SettingsNavigation? = null,
    state: SettingsState = SettingsState(),
    onLogoutClick: () -> Unit = {},
) {
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column {
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.surfaceContainer,
                        shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                    )
                    .shadow(4.dp, shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                    .windowInsetsTopHeight(WindowInsets.systemBars)
            )
            state.settingProviders.forEach { settingProvider ->
                SettingGroup(settingProvider, { settingsNavigation?.navigateTo(it) })
            }


            Spacer(Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                contentPadding = PaddingValues(8.dp),
                onClick = onLogoutClick
            ) {
                Text("Logout")
            }
        }
    }
}

@Composable
fun PickDatabaseDialog(
    onFileSelected: (Uri) -> Unit,
    onDismiss: () -> Unit
) {
    var pickedFileName by remember { mutableStateOf<String?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument(),
        onResult = { uri ->
            uri?.let {
                pickedFileName = uri.lastPathSegment
                onFileSelected(uri)
            }
        }
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Database File") },
        text = {
            Column {
                Text(pickedFileName ?: "No file selected")
                Spacer(Modifier.height(16.dp))
                Button(onClick = {
                    launcher.launch(arrayOf("*/*"))  // You can filter to "application/octet-stream" or "application/x-sqlite3"
                }) {
                    Text("Choose File")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

@Composable
fun SettingGroup(settingProvider: SettingProvider, onSettingsClick: (route: Any) -> Unit = {}) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer, shape = RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = settingProvider.title(), Modifier.padding(4.dp), style = MaterialTheme.typography.labelLarge)
        settingProvider.provideList().forEach { setting ->
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable { onSettingsClick(setting.route) }
                    .background(MaterialTheme.colorScheme.surfaceVariant, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Text(text = setting.title, Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Filled.NavigateNext, setting.title)
            }
        }
    }
}

@Composable
fun SettingsScreenPreview() {
    TapMyDayTheme {
        SettingsScreenContent(
            state = SettingsState(
                setOf(
                    object : SettingProvider {
                        override fun title(): String {
                            return "Group"
                        }

                        override fun priority(): Int = 5

                        override fun provideList(): List<SettingProvider.Setting> {
                            return listOf(
                                SettingProvider.Setting("Settings1", Any()),
                                SettingProvider.Setting("Settings2", Any()),
                            )
                        }
                    }
                ))
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL or Configuration.UI_MODE_NIGHT_NO)
@Composable
fun SettingsScreenPreviewLight() {
    SettingsScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL or Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenPreviewDark() {
    SettingsScreenPreview()
}
