package com.nicholas.rutherford.potter.head.entry.point

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelProvider

/**
 * CompositionLocal for providing ViewModelFactory throughout the app.
 * This allows ViewModels to access the factory via CompositionLocal without explicit passing.
 */
val LocalViewModelFactory = compositionLocalOf<ViewModelProvider.Factory> {
    error("ViewModelFactory not provided. Make sure to provide it in your Activity/Composable.")
}

