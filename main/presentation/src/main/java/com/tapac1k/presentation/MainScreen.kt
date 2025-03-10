package com.tapac1k.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day_list.contract_ui.DayListRouter
import com.tapac1k.settings.contract_ui.SettingsRouter
import dagger.Lazy

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    dayListRouter: Lazy<DayListRouter>? = null,
    settingsRouter: Lazy<SettingsRouter>? = null,
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
            dayListRouter,
            settingsRouter,
        )
    }
}

@Composable
fun MainScreenContent(
    dayListRouter: Lazy<DayListRouter>? = null,
    settingsRouter: Lazy<SettingsRouter>? = null,
) {
    val navController = rememberNavController()
    val startRoute = "day-list"
    NavHost(navController, startDestination = startRoute) {
        composable("day-list") { backStackEntry ->
            dayListRouter?.get()?.NavigateDayList(
                onSettings = {
                    navController.navigate("settings")
                },
                openDay = {

                })
        }
        composable("settings") { navBackStackEntry ->
            settingsRouter?.get()?.NavigateToSettings()
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