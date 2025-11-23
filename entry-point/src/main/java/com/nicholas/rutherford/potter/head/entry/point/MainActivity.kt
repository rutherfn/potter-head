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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme
import com.nicholas.rutherford.potter.head.entry.point.navigation.AppNavigationGraph
import com.nicholas.rutherford.potter.head.entry.point.navigation.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            // Access ViewModelFactory from Application via public interface (no reflection)
            val application = context.applicationContext
            val viewModelFactory = (application as? ViewModelFactoryProvider)
                ?.getViewModelFactory()
                ?: throw IllegalStateException("Application must implement ViewModelFactoryProvider")
            
            CompositionLocalProvider(LocalViewModelFactory provides viewModelFactory) {
                PotterHeadTheme {
                    val factory = LocalViewModelFactory.current
                    val viewModel: MainActivityViewModel = viewModel(factory = factory)
                    val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

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