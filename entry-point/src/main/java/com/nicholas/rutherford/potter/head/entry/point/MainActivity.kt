package com.nicholas.rutherford.potter.head.entry.point

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nicholas.rutherford.potter.head.base.view.model.LocalViewModelFactory
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme
import com.nicholas.rutherford.potter.head.core.theme.buildThemePreferenceFromValue
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

        val dependencies = getApplicationDependencies(context = this)
        val startupThemePreference = dependencies.dataStorePreferenceReader.peekThemePreferenceValue()

        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current
            val themePreference by dependencies.dataStorePreferenceReader
                .readThemePreferenceValueFlow()
                .collectAsState(initial = startupThemePreference)
            val systemInDarkTheme = isSystemInDarkTheme()
            val useDarkTheme = buildThemePreferenceFromValue(themePreference)
                .isDarkTheme(systemInDarkTheme = systemInDarkTheme)

            LaunchedEffect(themePreference) {
                applyThemePreferenceNightMode(themePreferenceValue = themePreference)
            }

            CompositionLocalProvider(
                LocalViewModelFactory provides dependencies.viewModelFactory,
                LocalAppBarFactory provides dependencies.appBarFactory
            ) {
                PotterHeadTheme(darkTheme = useDarkTheme) {
                    val navController = rememberNavController()
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    NavigationSideEffects(
                        activity = this,
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
