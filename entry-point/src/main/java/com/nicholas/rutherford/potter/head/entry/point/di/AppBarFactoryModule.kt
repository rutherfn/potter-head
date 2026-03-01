package com.nicholas.rutherford.potter.head.entry.point.di

import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory

/**
 * Module interface for providing AppBar factory instances.
 *
 * This module provides access to [AppBarFactory] which can be used to create
 * AppBar instances for different screens in the application.
 *
 * @property appBarFactory The factory instance used to create AppBar instances
 *                         for various screens (e.g., characters screen, default app bar).
 *
 * @author Nicholas Rutherford
 */
interface AppBarFactoryModule {

    val appBarFactory: AppBarFactory
}