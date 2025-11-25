package com.nicholas.rutherford.potter.head.compose.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.Flow

/**
 * Extension function that sets up a lifecycle-aware collection of a [Flow] and exposes it as [State].
 *
 * This function automatically stops collecting the flow when the [LifecycleOwner] is not in a
 * STARTED state or higher, preventing unnecessary work and potential memory leaks. The flow will
 * automatically resume collection when the lifecycle returns to STARTED state.
 *
 * This is particularly useful for collecting StateFlows from ViewModels or other sources that
 * should only be active when the UI is visible and active.
 *
 * Usage:
 * ```
 * val lifecycleOwner = LocalLifecycleOwner.current
 * val navigationAction by navigator.navActions.asLifecycleAwareState(
 *     lifecycleOwner = lifecycleOwner,
 *     initialState = null
 * )
 * ```
 *
 * @param lifecycleOwner The [LifecycleOwner] controlling the active state of the flow collection.
 * @param initialState The default value returned before any emissions.
 * @return A Compose [State] representing the current value emitted by the flow.
 */
@Composable
fun <T> Flow<T>.asLifecycleAwareState(
    lifecycleOwner: LifecycleOwner,
    initialState: T
): State<T> =
    lifecycleAwareState(lifecycleOwner, this, initialState)

/**
 * Internal utility that sets up a lifecycle-aware collection of a [Flow] and exposes it as [State].
 *
 * Used internally by [asLifecycleAwareState] to avoid collecting when the [LifecycleOwner]
 * is not in a STARTED state or higher.
 *
 * @param lifecycleOwner The [LifecycleOwner] controlling the active state of the flow collection.
 * @param flow The [Flow] to collect.
 * @param initialState The default value returned before any emissions.
 * @return A Compose [State] representing the current value emitted by the flow.
 */
@Composable
internal fun <T> lifecycleAwareState(
    lifecycleOwner: LifecycleOwner,
    flow: Flow<T>,
    initialState: T
): State<T> {
    return flow.collectAsStateWithLifecycle(
        initialValue = initialState,
        lifecycle = lifecycleOwner.lifecycle,
        minActiveState = Lifecycle.State.STARTED
    )
}

