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
    fun createQuizDetailAppBar(onIconButtonClicked: (() -> Unit)? = null): AppBar
    fun createTakeQuizAppBar(quizTitle: String?, onIconButtonClicked: (() -> Unit)?): AppBar
    fun createQuizResultAppBar(onIconButtonClicked: (() -> Unit)?): AppBar
    fun createSpellsAppBar(): AppBar
    fun createQuizzesAppBar(): AppBar
}
