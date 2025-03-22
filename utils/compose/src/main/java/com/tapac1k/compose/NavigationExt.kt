package com.tapac1k.compose

import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import kotlin.reflect.KClass

fun <T : Any> NavController.safeNavigate(currentRoute: KClass<T>, route: Any) {
    if (currentBackStackEntry?.destination?.hasRoute(currentRoute) == true) {
        navigate(route)
    }
}
