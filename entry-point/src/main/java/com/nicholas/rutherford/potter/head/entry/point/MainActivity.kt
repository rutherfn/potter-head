package com.nicholas.rutherford.potter.head.entry.point

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.base.view.model.NavigatorProvider
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.compose.ui.theme.asLifecycleAwareState
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme
import com.nicholas.rutherford.potter.head.entry.point.navigation.AppNavigationGraph
import com.nicholas.rutherford.potter.head.entry.point.navigation.Screens
import com.nicholas.rutherford.potter.head.navigation.Navigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current
            val application = context.applicationContext
            val viewModelFactory = (application as? ViewModelFactoryProvider)
                ?.getViewModelFactory()
                ?: throw IllegalStateException("Application must implement ViewModelFactoryProvider")
            
            // Get Navigator from NavigatorProvider interface
            val navigator: Navigator = (application as? NavigatorProvider)
                ?.getNavigator()
                ?: throw IllegalStateException("Application must implement NavigatorProvider")

            val navActionState by navigator.navActions.asLifecycleAwareState(
                lifecycleOwner = lifecycleOwner,
                initialState = null
            )
            val popRouteState by navigator.popRouteActions.asLifecycleAwareState(
                lifecycleOwner = lifecycleOwner,
                initialState = null
            )
            
            // Debug: Log when navActionState changes
            LaunchedEffect(navActionState) {
                println("DEBUG: navActionState changed to: ${navActionState?.destination}")
            }
            
            CompositionLocalProvider(value = LocalViewModelFactory provides viewModelFactory) {
                PotterHeadTheme {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    // Observe navigation actions
                    // Use the destination string as the key to ensure LaunchedEffect triggers on each navigation
                    // This ensures it triggers even if the NavigationAction object reference is the same
                    LaunchedEffect(navActionState?.destination) {
                        println("DEBUG: LaunchedEffect triggered with destination: ${navActionState?.destination}")
                        navActionState?.let { state ->
                            println("navigation state destination ${state.destination}")
                            navController.navigate(state.destination, state.navOptions)
                            // Reset after navigation to allow the same destination to be navigated to again
                            navigator.resetNavAction()
                        }
                    }

                    // Observe pop actions
                    // Only execute when popRouteState is not null (i.e., when there's an actual pop action)
                    // Use the route value as the key, but only execute when it's not null
                    LaunchedEffect(popRouteState) {
                        // Only execute if popRouteState is not null (i.e., an actual pop action was triggered)
                        // Don't execute on initial composition when it's null
                        popRouteState?.let { route ->
                            navController.popBackStack(route = route, inclusive = false)
                            navigator.resetPopAction()
                        }
                    }

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                                NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.Characters.route } == true,
                                onClick = {
                                    navController.navigate(Screens.Characters.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Default.Group,
                                        contentDescription = Screens.Characters.title
                                    )
                                },
                                label = { Text(Screens.Characters.title) }
                            )
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.Quizzes.route } == true,
                                onClick = {
                                    navController.navigate(Screens.Quizzes.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Default.Quiz,
                                        contentDescription = Screens.Quizzes.title
                                    )
                                },
                                label = { Text(Screens.Quizzes.title) }
                            )
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == Screens.Settings.route } == true,
                                onClick = {
                                    navController.navigate(Screens.Settings.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        Icons.Default.Settings,
                                        contentDescription = Screens.Settings.title
                                    )
                                },
                                label = { Text(Screens.Settings.title) }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Characters.route,
                        modifier = Modifier.padding(paddingValues = innerPadding)
                    ) {
                        with(receiver = AppNavigationGraph) {
                            charactersScreen()
                            characterDetailScreen()
                            quizzesScreen()
                            settingsScreen()
                        }
                    }
                }
                }
            }
        }
    }
}