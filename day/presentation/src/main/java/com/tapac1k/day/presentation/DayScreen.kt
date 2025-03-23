package com.tapac1k.day.presentation

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.compose.widgets.TopBar
import com.tapac1k.day.contract.DayActivity
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.presentation.widget.ActivityRings
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.tapac1k.compose.LifecycleEffect

@Composable
fun DayScreen(
    viewModel: DayViewModel = viewModel(),
    dayNavigation: DayNavigation
) {
    val state by  viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    LifecycleEffect(
        lifecycleOwner,
        onStop = {
            viewModel.saveDay()
        }
    )
    DayScreenContent(
        state,
        stateUpdater = viewModel::updateState,
        onBack = dayNavigation::onBack,
    )
}

@Composable
fun DayScreenContent(
    dayState: DayState = DayState(),
    stateUpdater: (StateUpdate) -> Unit = {},
    onBack:() -> Unit = {},
) {
    Surface(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)) {
        Column(Modifier.windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))) {
            TopBar {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Rounded.ArrowBack, "")
                }
            }

            LazyColumn {
                item("Rings") {
                    ActivityRings(
                        dayState.dayActivity,
                        onMoodUpdate = { stateUpdater(StateUpdate.UpdateMood(it)) },
                        onStateUpdate = { stateUpdater(StateUpdate.UpdateState(it)) },
                        onSleepUpdate = { stateUpdater(StateUpdate.UpdateSleepHours(it)) },)
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewDayScreen() {
    TapMyDayTheme {
        DayScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PreviewDayScreenDark() {
    TapMyDayTheme {
        DayScreenContent()
    }
}