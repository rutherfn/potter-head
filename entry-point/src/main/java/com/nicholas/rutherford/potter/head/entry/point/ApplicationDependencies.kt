package com.nicholas.rutherford.potter.head.entry.point

import androidx.lifecycle.ViewModelProvider
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Data class containing application-level dependencies required by the activity.
 *
 * @property viewModelFactory The [ViewModelProvider.Factory] for creating ViewModels
 * @property navigator The [Navigator] for handling navigation actions
 * @property appBarFactory The [AppBarFactory] for creating AppBar instances
 *
 * @author Nicholas Rutherford
 */
data class ApplicationDependencies(
    val viewModelFactory: ViewModelProvider.Factory,
    val navigator: Navigator,
    val appBarFactory: AppBarFactory
)
