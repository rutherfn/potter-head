package com.nicholas.rutherford.potter.head.di

import com.nicholas.rutherford.potter.head.entry.point.di.AppBarFactoryModule
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactoryImpl

/**
 * Implementation of [AppBarFactoryModule].
 * Provides the app bar factory for creating app bars throughout the application.
 *
 * @author Nicholas Rutherford
 */
class AppBarFactoryModuleImpl : AppBarFactoryModule {
    override val appBarFactory: AppBarFactory = AppBarFactoryImpl()
}


