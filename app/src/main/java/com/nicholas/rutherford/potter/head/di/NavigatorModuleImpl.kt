package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.NavigatorImpl
import com.nicholas.rutherford.potter.head.navigation.di.NavigatorModule

/**
 * Implementation of [NavigatorModule].
 * Provides the navigator instance for handling navigation actions throughout the application.
 *
 * @author Nicholas Rutherford
 */
internal class NavigatorModuleImpl : NavigatorModule {
    private val navigatorInstance: Navigator = NavigatorImpl()

    override val navigator: Navigator = navigatorInstance
}

