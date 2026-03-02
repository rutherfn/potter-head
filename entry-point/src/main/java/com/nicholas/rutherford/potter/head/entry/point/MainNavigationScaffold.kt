package com.nicholas.rutherford.potter.head.entry.point

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.nicholas.rutherford.potter.head.base.view.model.asLifecycleAwareState
import com.nicholas.rutherford.potter.head.compose.components.MainAppBar
import com.nicholas.rutherford.potter.head.entry.point.navigation.AppNavigationGraph
import com.nicholas.rutherford.potter.head.entry.point.navigation.Screens
import com.nicholas.rutherford.potter.head.entry.point.navigation.bottom.BottomNavigationBar

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
            if (Screens.shouldShowBottomNavigation(route = currentDestination?.route)) {
                BottomNavigationBar(
                    navController = navController,
                    currentDestination = currentDestination
                )
            }
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