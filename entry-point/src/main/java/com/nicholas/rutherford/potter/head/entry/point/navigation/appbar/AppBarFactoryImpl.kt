package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

import com.nicholas.rutherford.potter.head.core.StringIds

/**
 * Default implementation of [AppBarFactory].
 *
 * @author Nicholas Rutherford
 */
class AppBarFactoryImpl : AppBarFactory {

    override fun createCharactersAppBar(): AppBar = AppBar(titleId = StringIds.characters, imageVector = null)
}