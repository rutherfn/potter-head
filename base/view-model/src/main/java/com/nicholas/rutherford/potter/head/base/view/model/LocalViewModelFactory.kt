package com.nicholas.rutherford.potter.head.base.view.model

import androidx.compose.runtime.compositionLocalOf
import androidx.lifecycle.ViewModelProvider

/**
 * CompositionLocal for providing ViewModelFactory throughout the app.
 * This allows ViewModels to access the factory via CompositionLocal without explicit passing.
 *
 * Usage:
 * ```
 * val factory = LocalViewModelFactory.current
 * val viewModel: MyViewModel = viewModel(factory = factory)
 * ```
 *
 * @author Nicholas Rutherford
 */
val LocalViewModelFactory = compositionLocalOf<ViewModelProvider.Factory> {
    error("ViewModelFactory not provided. Make sure to provide it in your Activity/Composable.")
}
