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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme

sealed class Screen(val route: String, val title: String) {
    object Characters : Screen("characters", "Characters")
    object Quizzes : Screen("quizzes", "Quizzes")
    object Settings : Screen("settings", "Settings")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PotterHeadTheme {
                val navController = rememberNavController()
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == Screen.Characters.route } == true,
                                onClick = {
                                    navController.navigate(Screen.Characters.route) {
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
                                        contentDescription = Screen.Characters.title
                                    )
                                },
                                label = { Text(Screen.Characters.title) }
                            )
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == Screen.Quizzes.route } == true,
                                onClick = {
                                    navController.navigate(Screen.Quizzes.route) {
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
                                        contentDescription = Screen.Quizzes.title
                                    )
                                },
                                label = { Text(Screen.Quizzes.title) }
                            )
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == Screen.Settings.route } == true,
                                onClick = {
                                    navController.navigate(Screen.Settings.route) {
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
                                        contentDescription = Screen.Settings.title
                                    )
                                },
                                label = { Text(Screen.Settings.title) }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Characters.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Characters.route) {
                            CharactersScreen()
                        }
                        composable(Screen.Quizzes.route) {
                            QuizzesScreen()
                        }
                        composable(Screen.Settings.route) {
                            SettingsScreen()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharactersScreen() {
    Text(
        text = "Characters Screen",
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun QuizzesScreen() {
    Text(
        text = "Quizzes Screen",
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun SettingsScreen() {
    Text(
        text = "Settings Screen",
        modifier = Modifier.padding(16.dp)
    )
}