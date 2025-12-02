package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavController
import com.nicholas.rutherford.potter.head.compose.ui.theme.asLifecycleAwareState
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Composable function that handles navigation effects from the [Navigator].
 *
 * This function sets up [LaunchedEffect] handlers that respond to navigation actions
 * and pop route actions from the [Navigator]. It automatically:
 * - Navigates when a navigation action is triggered
 * - Pops the back stack when a pop route action is triggered
 * - Resets the actions after they are processed
 *
 * This should be called once in the composition, typically at the top level
 * of the navigation setup.
 *
 * @param navController The [NavController] used to perform navigation
 * @param navigator The [Navigator] that provides navigation actions
 * @param lifecycleOwner The [LifecycleOwner] used for lifecycle-aware state collection
 *
 * @author Nicholas Rutherford
 */
@Composable
fun NavigationEffects(
    navController: NavController,
    navigator: Navigator,
    lifecycleOwner: LifecycleOwner
) {
    val navActionState by navigator.navActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )
    val popRouteState by navigator.popRouteActions.asLifecycleAwareState(
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
}

