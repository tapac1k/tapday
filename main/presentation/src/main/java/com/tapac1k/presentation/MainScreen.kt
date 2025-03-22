package com.tapac1k.presentation

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.MenuBook
import androidx.compose.material.icons.automirrored.outlined.MenuBook
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsGymnastics
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.SportsGymnastics
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.tapac1k.compose.safeNavigate
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.day.contract_ui.DayListRoute
import com.tapac1k.day.contract_ui.DayRoute
import com.tapac1k.day.contract_ui.DayListNavigation
import com.tapac1k.day.contract_ui.DayNavigation
import com.tapac1k.day.contract_ui.DayRouter
import com.tapac1k.settings.contract_ui.SettingsNavigation
import com.tapac1k.settings.contract_ui.SettingsRouter
import com.tapac1k.training.contract.TrainingTag
import com.tapac1k.training.contract_ui.ExerciseDetailsRoute
import com.tapac1k.training.contract_ui.ExerciseListNavigation
import com.tapac1k.training.contract_ui.ExerciseListRoute
import com.tapac1k.training.contract_ui.TrainingListRoute
import com.tapac1k.training.contract_ui.TrainingRouter
import com.tapac1k.training.contract_ui.TrainingTagNavigation
import com.tapac1k.training.contract_ui.TrainingTagRoute
import com.tapac1k.training.contract_ui.TrainingTagsRoute
import com.tapac1k.utils.common.WithBackNavigation
import dagger.Lazy
import kotlin.reflect.KClass

@Composable
fun MainScreen(
    viewModel: MainViewModel = viewModel(),
    settingsRouter: Lazy<SettingsRouter>? = null,
    dayRouter: Lazy<DayRouter>? = null,
    trainingRouter: Lazy<TrainingRouter>? = null,
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
            settingsRouter,
            dayRouter,
            trainingRouter,
        )
    }
}

@Composable
fun MainScreenContent(
    settingsRouter: Lazy<SettingsRouter>? = null,
    dayRouter: Lazy<DayRouter>? = null,
    trainingRouter: Lazy<TrainingRouter>? = null,
) {
    val navController = rememberNavController()
    val defaultBackController = DefaultBackNavigation(navController)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        bottomBar = {
            val currentDestination = navBackStackEntry?.destination
            val visible = !topLevelDestinations.all { currentDestination?.hasRoute(it.route::class) == false }
            AnimatedVisibility(
                visible = visible,
                exit = slideOutVertically(targetOffsetY = { it }) + fadeOut(),
                enter = slideInVertically(initialOffsetY = { it }) + fadeIn()
            ) {
                BottomNavigation(
                    backgroundColor = MaterialTheme.colorScheme.surfaceContainer,
                ) {
                    topLevelDestinations.forEach { topLevelRoute ->
                        val selected = currentDestination?.hasRoute(topLevelRoute.route::class) == true
                        BottomNavigationItem(
                            modifier = Modifier.alpha(if (selected) 1f else 0.5f),
                            icon = {
                                Icon(
                                    if (selected) topLevelRoute.selectedIcon else topLevelRoute.unselectedIcon,
                                    contentDescription = topLevelRoute.name
                                )
                            },
                            label = { Text(topLevelRoute.name, style = MaterialTheme.typography.labelSmall) },
                            onClick = {
                                navController.navigate(topLevelRoute.route) {
                                    popUpTo(0) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            selected = selected,
                        )
                    }
                }
            }

        }
    ) { paddingValues ->
        NavHost(
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            navController = navController,
            startDestination = DayListRoute
        ) {
            composable<DayListRoute> { backStackEntry ->
                dayRouter?.get()?.NavigateDayList(object : DayListNavigation {
                    override fun openDayDetails(dayId: Long) {
                        navController.safeNavigate(DayListRoute::class, DayRoute(dayId))
                    }
                })
            }
            composable<DayRoute> {
                dayRouter?.get()?.NavigateDayScreen(object : DayNavigation, WithBackNavigation by defaultBackController {})
            }
            composable<Settings> { navBackStackEntry ->
                settingsRouter?.get()?.NavigateToSettings(object : SettingsNavigation {
                    override fun navigateTo(route: Any) {
                        navController.safeNavigate(Settings::class, route)
                    }
                })
            }
            trainingRouter?.get()?.apply {
                initGraph(navController, defaultBackController)
            }
        }
    }
}

private class DefaultBackNavigation(private val navController: NavController) : WithBackNavigation {
    override fun onBack() {
        navController.navigateUp()
    }
}

private val topLevelDestinations = listOf(
    Destination
        (
        DayListRoute,
        "Day List",
        Icons.AutoMirrored.Filled.MenuBook,
        Icons.AutoMirrored.Outlined.MenuBook
    ),
    Destination(
        TrainingListRoute,
        "Trainings",
        Icons.Filled.SportsGymnastics,
        Icons.Outlined.SportsGymnastics,
    ),
    Destination(
        Settings,
        "Settings",
        Icons.Filled.Settings,
        Icons.Outlined.Settings,
    ),
)

private data class Destination<T : Any>(
    val route: T,
    val name: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

@Composable
fun MainScreenPreview() {
    TapMyDayTheme {
        MainScreenContent()
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MainScreenPreviewLight() {
    MainScreenPreview()
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainScreenPreviewDark() {
    MainScreenPreview()
}
