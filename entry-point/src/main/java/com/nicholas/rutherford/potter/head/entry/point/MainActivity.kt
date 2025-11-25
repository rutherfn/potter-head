package com.nicholas.rutherford.potter.head.entry.point

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme
import com.nicholas.rutherford.potter.head.entry.point.navigation.AppNavigationGraph
import com.nicholas.rutherford.potter.head.entry.point.navigation.BottomNavigationBar
import com.nicholas.rutherford.potter.head.entry.point.navigation.NavigationEffects
import com.nicholas.rutherford.potter.head.entry.point.navigation.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current
            val dependencies = getApplicationDependencies(context)

            CompositionLocalProvider(value = LocalViewModelFactory provides dependencies.viewModelFactory) {
                PotterHeadTheme {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    NavigationEffects(
                        navController = navController,
                        navigator = dependencies.navigator,
                        lifecycleOwner = lifecycleOwner
                    )

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
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