package com.basta.guessemoji.common.utils

/**
 * A reusable Composable that observes the Lifecycle events of the given [LifecycleOwner].
 * Use this function to easily track the different lifecycle events and perform actions accordingly.
 *
 * Usage:
 * --------------
 * OnLifecycleEvent { owner, event ->
 *     when (event) {
 *         Lifecycle.Event.ON_CREATE -> Log.d("LifeCycle", "ON_CREATE: The lifecycle has just been created.")
 *         Lifecycle.Event.ON_START -> Log.d("LifeCycle", "ON_START: The Composable is visible and receives focus.")
 *         Lifecycle.Event.ON_RESUME -> Log.d("LifeCycle", "ON_RESUME: The Composable is active and ready to interact.")
 *         Lifecycle.Event.ON_PAUSE -> Log.d("LifeCycle", "ON_PAUSE: The Composable loses focus but remains visible.")
 *         Lifecycle.Event.ON_STOP -> Log.d("LifeCycle", "ON_STOP: The Composable is no longer visible.")
 *         else -> Log.d("LifeCycle", "else: An unexpected lifecycle event occurred.")
 *     }
 * }
 * --------------
 *
 * Lifecycle.Event.ON_CREATE: The lifecycle has just been created. This event is triggered once
 * at the beginning of the Composable's lifecycle.
 *
 * Lifecycle.Event.ON_START: The Composable is visible and receives focus. Use this event for
 * initialization tasks that should run when the Composable becomes visible.
 *
 * Lifecycle.Event.ON_RESUME: The Composable is active and ready to interact. Use this event for
 * tasks that should be performed when the Composable is actively being used.
 *
 * Lifecycle.Event.ON_PAUSE: The Composable loses focus but remains visible. Use this event to
 * pause ongoing operations or clean up resources temporarily while the Composable is not in focus.
 *
 * Lifecycle.Event.ON_STOP: The Composable is no longer visible. Use this event to release
 * resources or perform cleanup tasks when the Composable is no longer needed in the UI.
 *
 * Lifecycle.Event.ON_DESTROY: The Composable is being removed from the UI hierarchy. Note that
 * Compose does not have a direct equivalent to ON_DESTROY. Instead, use `onDispose` for cleanup
 * operations when the Composable is removed from the composition.
 */

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun OnLifecycleEvent(onEvent: (owner: LifecycleOwner, event: Lifecycle.Event) -> Unit) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}