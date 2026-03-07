package com.nicholas.rutherford.potter.head.entry.point

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme
import com.nicholas.rutherford.potter.head.entry.point.navigation.LocalAppBarFactory
import com.nicholas.rutherford.potter.head.entry.point.navigation.NavigationSideEffects

/**
 * Main entry point Activity for the Potter Head application.
 * Sets up edge-to-edge display, dependency injection, and navigation.
 *
 * @author Nicholas Rutherford
 */
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val context = LocalContext.current
            val dependencies = getApplicationDependencies(context = context)

            CompositionLocalProvider(
                LocalViewModelFactory provides dependencies.viewModelFactory,
                LocalAppBarFactory provides dependencies.appBarFactory
            ) {
                PotterHeadTheme {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    NavigationSideEffects(
                        navController = navController,
                        navigator = dependencies.navigator,
                        lifecycleOwner = lifecycleOwner
                    )
                    
                    MainNavigationScaffold(
                        navController = navController,
                        lifecycleOwner = lifecycleOwner,
                        currentDestination = currentDestination
                    )
                }
            }
        }
    }
}