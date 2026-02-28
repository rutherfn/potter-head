package com.nicholas.rutherford.potter.head.entry.point.di

import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBarFactory

/**
 * Interface for providing AppBarFactory access.
 *
 * Classes implementing this interface can provide access to an [AppBarFactory]
 * instance, which is used to create AppBar instances for different screens.
 *
 * @author Nicholas Rutherford
 */
interface AppBarFactoryProvider {

    fun getAppBarFactory(): AppBarFactory
}

