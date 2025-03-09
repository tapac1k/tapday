package com.tapac1k.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.login.presentation.AuthState

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoggedIn: () -> Unit= {},
) {
    Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
        val state by viewModel.state.collectAsState()
        LaunchedEffect(Unit) {
            viewModel.events.collect { event ->
                if (event is AuthEvent.LoggedIn) {
                    onLoggedIn() // ✅ Call onLoggedIn() when the event occurs
                }
            }
        }
        Content(
            state,
            onLogin = {viewModel.login()} ,
        )
    }
}

@Composable
fun Content(
    state: AuthState,
    onLogin: () -> Unit = {},
) {
    when(state) {
        AuthState.Loading -> {
            // Implement your loading screen UI here
            Text(text = "Loading")
        }
        AuthState.Error -> {
            Text(text = "Loading")
        }
        AuthState.LoggedOut -> {
            Button(onClick = {
                onLogin()
            }) {
                Text("login")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    TapMyDayTheme {
        AuthScreen()
    }
}