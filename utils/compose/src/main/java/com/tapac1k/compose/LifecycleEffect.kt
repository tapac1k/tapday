package com.tapac1k.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun LifecycleEffect(
    lifecycleOwner: LifecycleOwner,
    onPause: (() -> Unit)? = null,
    onResume: (() -> Unit)? = null,
    onStart: (() -> Unit)? = null,
    onStop: (() -> Unit)? = null,
    onCreate: (() -> Unit)? = null,
    onDestroy: (() -> Unit)? = null,
) {

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { source, event ->
            when (event) {
                Lifecycle.Event.ON_CREATE -> onCreate?.invoke()
                Lifecycle.Event.ON_START -> onStart?.invoke()
                Lifecycle.Event.ON_RESUME -> onResume?.invoke()
                Lifecycle.Event.ON_PAUSE -> onPause?.invoke()
                Lifecycle.Event.ON_STOP -> onStop?.invoke()
                Lifecycle.Event.ON_DESTROY -> onDestroy?.invoke()
                Lifecycle.Event.ON_ANY -> Unit
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
}