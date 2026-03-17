package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.runtime.compositionLocalOf
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory

/**
 * CompositionLocal for providing AppBarFactory throughout the app.
 * This allows composables to access the AppBarFactory via CompositionLocal without explicit passing.
 *
 * Usage:
 * ```
 * val appBarFactory = LocalAppBarFactory.current
 * val appBar = appBarFactory.createCharactersAppBar()
 * ```
 *
 * @author Nicholas Rutherford
 */
val LocalAppBarFactory = compositionLocalOf<AppBarFactory> {
    error("AppBarFactory not provided. Make sure to provide it in your Activity/Composable.")
}



