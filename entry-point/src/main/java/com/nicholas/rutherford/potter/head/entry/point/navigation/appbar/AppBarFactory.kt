package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

/**
 * Factory interface for creating [AppBar] instances for different screens.
 *
 * @author Nicholas Rutherford
 */
interface AppBarFactory {
    fun createCharactersAppBar(): AppBar
    fun createFiltersAppBar(onIconButtonClicked: (() -> Unit)? = null): AppBar
    fun createCharacterDetailAppBar(onIconButtonClicked: (() -> Unit)? = null): AppBar
    fun createSpellsAppBar(): AppBar
    fun createQuizzesAppBar(): AppBar
}