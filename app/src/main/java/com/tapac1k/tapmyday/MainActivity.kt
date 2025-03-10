package com.tapac1k.tapmyday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.contract_ui.AuthRouter
import com.tapac1k.main.contract_ui.MainRouter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var authRouter: AuthRouter

    @Inject
    lateinit var mainRouter: MainRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TapMyDayTheme {
                MyApp()
            }
        }
    }


    @Composable
    fun MyApp(modifier: Modifier = Modifier) {
        val navController = rememberNavController()
        val startRoute = "login"
        NavHost(navController, startDestination = startRoute) {
            composable("login") { backStackEntry ->
                println(authRouter::class.simpleName)
                authRouter.NavigateToLogin(
                    onLoggedIn = {
                    navController.navigate("main", NavOptions.Builder().setPopUpTo("login", true).build())
                })
            }
            composable("main") { navBackStackEntry ->
                mainRouter.NavigateToMain(onLoggedOut = {
                    navController.navigate("login", NavOptions.Builder().setPopUpTo("main", true).build())
                })
            }
        }
    }

    @Preview
    @Composable
    fun PreviewWaterCounter() {
        TapMyDayTheme {
            MyApp()
        }
    }
}