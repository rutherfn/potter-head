package com.nicholas.rutherford.potter.head.entry.point

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.Factory
import com.nicholas.rutherford.potter.head.base.view.model.NavigatorProvider
import com.nicholas.rutherford.potter.head.base.view.model.ViewModelFactoryProvider
import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryProvider
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory
import com.nicholas.rutherford.potter.head.navigation.Navigator

/**
 * Retrieves application dependencies from the application context.
 *
 * This function extracts the [ViewModelProvider.Factory], [Navigator], and [AppBarFactory]
 * from the application context, which must implement [ViewModelFactoryProvider],
 * [NavigatorProvider], and [AppBarFactoryProvider] respectively.
 *
 * @param context The [Context] from which to retrieve the application context
 * @return [ApplicationDependencies] containing the ViewModelFactory, Navigator, and AppBarFactory
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

    val appBarFactory: AppBarFactory = (application as? AppBarFactoryProvider)
        ?.getAppBarFactory()
        ?: throw IllegalStateException("Application must implement AppBarFactoryProvider")

    return ApplicationDependencies(
        viewModelFactory = viewModelFactory,
        navigator = navigator,
        appBarFactory = appBarFactory
    )
}
