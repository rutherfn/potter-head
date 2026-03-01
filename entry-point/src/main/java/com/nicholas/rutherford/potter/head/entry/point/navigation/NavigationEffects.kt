package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nicholas.rutherford.potter.head.compose.components.MainAppBar
import com.nicholas.rutherford.potter.head.compose.components.ProgressDialog
import com.nicholas.rutherford.potter.head.base.view.model.asLifecycleAwareState
import com.nicholas.rutherford.potter.head.entry.point.navigation.bottom.BottomNavigationBar
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
    val navActionState by navigator.navActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )
    val popRouteState by navigator.popRouteActions.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
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

/**
 * Composable that provides the main navigation scaffold with AppBar and NavHost.
 * Handles the UI layout for the main navigation structure.
 *
 * @param navController The NavController for handling navigation.
 * @param lifecycleOwner The LifecycleOwner for observing state changes.
 * @param currentDestination The current destination in the navigation graph.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun MainNavigationScaffold(
    navController: NavHostController,
    lifecycleOwner: LifecycleOwner,
    currentDestination: NavDestination?
) {
    val currentAppBar by AppNavigationGraph.currentAppBar.asLifecycleAwareState(
        lifecycleOwner = lifecycleOwner,
        initialState = null
    )

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            currentAppBar?.let { appBar ->
                MainAppBar(
                    title = stringResource(appBar.titleId),
                    onIconButtonClicked = appBar.onIconButtonClicked,
                    iconContentDescription = appBar.iconContentDescription,
                    imageVector = appBar.imageVector
                )
            }
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.Characters.route,
            modifier = Modifier.padding(paddingValues = innerPadding)
        ) {
            AppNavigationGraph.setupAllScreens(builder = this)
        }
    }
}

