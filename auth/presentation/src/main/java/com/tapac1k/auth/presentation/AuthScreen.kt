package com.tapac1k.auth.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Login
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.material.progressindicator.LinearProgressIndicator.IndicatorDirection
import com.tapac1k.compose.theme.TapMyDayTheme
import com.tapac1k.login.presentation.AuthState

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = viewModel(),
    onLoggedIn: () -> Unit= {},
) {
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)) {
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
    if (state == AuthState.Splash) return
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        GoogleButton(
            enabled = state == AuthState.LoggedOut,
            onClick = onLogin
        )
    }
}

@Composable
fun GoogleButton(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Button(
        enabled = enabled,
        onClick = onClick
    ) {
        if (enabled) {
            Icon(
                modifier = Modifier.padding(4.dp),
                imageVector = Icons.AutoMirrored.Filled.Login,
                contentDescription = "login with google"
            )
            Text("Google")
        } else {
            CircularProgressIndicator()
            Text("Loading...")
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun LoginScreenPreview() {
    TapMyDayTheme {
        Content(AuthState.LoggedOut)
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 640)
@Composable
fun LoginScreenPreviewLoading() {
    TapMyDayTheme {
        Content(AuthState.Loading)
    }
}