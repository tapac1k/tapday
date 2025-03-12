package com.tapac1k.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.contract.Day
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.day_list.contract_ui.DayListNavigation
import com.tapac1k.day_list.contract_ui.DayListRouter
import com.tapac1k.settings.contract_ui.SettingsNavigation
import com.tapac1k.settings.contract_ui.SettingsRouter
import com.tapac1k.utils.common.WithBackNavigation
import dagger.Lazy

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    dayListRouter: Lazy<DayListRouter>? = null,
    settingsRouter: Lazy<SettingsRouter>? = null,
    dayRouter: Lazy<DayRouter>? = null,
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
            dayRouter
        )
    }
}

@Composable
fun MainScreenContent(
    dayListRouter: Lazy<DayListRouter>? = null,
    settingsRouter: Lazy<SettingsRouter>? = null,
    dayRouter: Lazy<DayRouter>? = null,
) {
    val navController = rememberNavController()
    val defaultBackController = DefaultBackNavigation(navController)
    NavHost(navController, startDestination = DayList) {
        composable<DayList> { backStackEntry ->
            dayListRouter?.get()?.NavigateDayList(object : DayListNavigation {
                override fun openDayDetails(dayId: Long) {
                    navController.navigate(Day(dayId))
                }

                override fun openSettings() {
                    navController.navigate(Settings)
                }
            })
        }
        composable<Day> {
            dayRouter?.get()?.NavigateDayScreen(object : DayNavigation, WithBackNavigation by defaultBackController {})
        }
        composable<Settings> { navBackStackEntry ->
            settingsRouter?.get()?.NavigateToSettings(object : SettingsNavigation, WithBackNavigation by defaultBackController {})
        }

    }
}

private class DefaultBackNavigation(private val navController: NavController) : WithBackNavigation {
    override fun onBack() {
        navController.navigateUp()
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    TapMyDayTheme {
        MainScreenContent()
    }
}