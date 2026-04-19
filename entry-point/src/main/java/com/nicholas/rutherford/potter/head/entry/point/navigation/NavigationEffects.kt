package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavHostController
import com.nicholas.rutherford.potter.head.base.view.model.asLifecycleAwareState
import com.nicholas.rutherford.potter.head.compose.components.AlertDialog
import com.nicholas.rutherford.potter.head.compose.components.ProgressDialog
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Composable that handles navigation side effects.
 * Sets up LaunchedEffect handlers for navigation actions and manages progress dialogs.
 * This composable only handles side effects and does not render UI layout.
 *
 * @param navController The NavController for handling navigation.
 * @param navigator The Navigator for managing navigation actions.
 * @param lifecycleOwner The LifecycleOwner for observing state changes.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun NavigationSideEffects(
    navController: NavHostController,
    navigator: Navigator,
    lifecycleOwner: LifecycleOwner
) {
    val alertActionState by navigator.alertActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )
    val navActionState by navigator.navActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )
    val popRouteState by navigator.popRouteActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )
    val popOnceRequest by navigator.popOnceRequests.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = false
    )

    val progressState by navigator.progressActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )

    LaunchedEffect(navActionState?.destination) {
        navActionState?.let { state ->
            navController.navigate(state.destination, state.navOptions)
            navigator.resetNavAction()
        }
    }

    LaunchedEffect(popRouteState) {
        popRouteState?.let { route ->
            navController.popBackStack(route = route, inclusive = false)
            navigator.resetPopAction()
        }
    }

    LaunchedEffect(popOnceRequest) {
        if (popOnceRequest) {
            navController.popBackStack()
            navigator.resetPopOnceRequest()
        }
    }

    alertActionState?.let { newAlert ->
        AlertDialog(
            title = newAlert.title,
            onDismissClicked = {
                navigator.alert(alertAction = null)
                newAlert.onDismissClicked?.invoke()
            },
            confirmButton = newAlert.confirmButton?.let { btn ->
                btn.buttonText to {
                    navigator.alert(alertAction = null)
                    btn.onButtonClicked?.invoke()
                }
            },
            dismissButton = newAlert.dismissButton?.let { btn ->
                btn.buttonText to {
                    navigator.alert(alertAction = null)
                    btn.onButtonClicked?.invoke()
                }
            },
            description = newAlert.description
        )
    }

        progressState?.let { progress ->
            ProgressDialog(
                onDismissClicked = {
                    if (progress.shouldBeAbleToBeDismissed) {
                        navigator.progress(progressAction = null)
                    }
                    progress.onDismissClicked?.invoke()
                }
            )
    }
}
