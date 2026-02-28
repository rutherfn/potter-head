package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.nicholas.rutherford.potter.head.navigation.ProgressAction

/**
 * Composable that handles navigation effects and provides the main navigation scaffold.
 * Sets up LaunchedEffect handlers for navigation actions and manages progress dialogs.
 *
 * @param navController The NavController for handling navigation.
 * @param navigator The Navigator for managing navigation actions.
 * @param lifecycleOwner The LifecycleOwner for observing state changes.
 * @param currentDestination The current destination in the navigation graph.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun NavigationEffects(
    navController: NavHostController,
    navigator: Navigator,
    lifecycleOwner: LifecycleOwner,
    currentDestination: NavDestination?
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

    var progress: ProgressAction? by remember { mutableStateOf(value = null) }

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

    LaunchedEffect(progressState) {
        progressState?.let { newProgress ->
            progress = newProgress
        } ?: run {
            progress = null
        }
    }

    progress?.let { newProgress ->
        ProgressDialog(
            onDismissClicked = {
                if (newProgress.shouldBeAbleToBeDismissed) {
                    navigator.progress(progressAction = null)
                    progress = null
                }
                newProgress.onDismissClicked?.invoke()
            }
        )
    }

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

