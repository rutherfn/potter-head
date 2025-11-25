package com.nicholas.rutherford.potter.head.entry.point

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.nicholas.rutherford.potter.head.base.view.model.NavigatorProvider
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Data class containing application-level dependencies required by the activity.
 *
 * @property viewModelFactory The [ViewModelProvider.Factory] for creating ViewModels
 * @property navigator The [Navigator] for handling navigation actions
 *
 * @author Nicholas Rutherford
 */
data class ApplicationDependencies(
    val viewModelFactory: ViewModelProvider.Factory,
    val navigator: Navigator
)

/**
 * Retrieves application dependencies from the application context.
 *
 * This function extracts the [ViewModelProvider.Factory] and [Navigator] from the
 * application context, which must implement [ViewModelFactoryProvider] and
 * [NavigatorProvider] respectively.
 *
 * @param context The [Context] from which to retrieve the application context
 * @return [ApplicationDependencies] containing the ViewModelFactory and Navigator
 * @throws IllegalStateException if the application doesn't implement the required providers
 *
 * @author Nicholas Rutherford
 */
fun getApplicationDependencies(context: Context): ApplicationDependencies {
    val application = context.applicationContext
    val viewModelFactory = (application as? ViewModelFactoryProvider)
        ?.getViewModelFactory()
        ?: throw IllegalStateException("Application must implement ViewModelFactoryProvider")
    
    val navigator: Navigator = (application as? NavigatorProvider)
        ?.getNavigator()
        ?: throw IllegalStateException("Application must implement NavigatorProvider")
    
    return ApplicationDependencies(
        viewModelFactory = viewModelFactory,
        navigator = navigator
    )
}

