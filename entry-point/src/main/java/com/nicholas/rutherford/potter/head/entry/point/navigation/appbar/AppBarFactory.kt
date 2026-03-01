package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

/**
 * Factory interface for creating [AppBar] instances for different screens.
 *
 * @author Nicholas Rutherford
 */
interface AppBarFactory {

    fun createCharactersAppBar(): AppBar
}