package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import com.nicholas.rutherford.potter.head.core.StringIds

/**
 * Default implementation of [AppBarFactory].
 *
 * @author Nicholas Rutherford
 */
class AppBarFactoryImpl : AppBarFactory {

    override fun createCharactersAppBar(): AppBar =
        AppBar(titleId = StringIds.characters, imageVector = null)

    override fun createFiltersAppBar(onIconButtonClicked: (() -> Unit)?): AppBar =
        AppBar(
            titleId = StringIds.filters,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            onIconButtonClicked = onIconButtonClicked,
            iconContentDescription = "Close"
        )

    override fun createCharacterDetailAppBar(onIconButtonClicked: (() -> Unit)?): AppBar =
        AppBar(
            titleId = StringIds.characterDetail,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            onIconButtonClicked = onIconButtonClicked,
            iconContentDescription = "Back"
        )

    override fun createQuizDetailAppBar(onIconButtonClicked: (() -> Unit)?): AppBar =
        AppBar(
            titleId = StringIds.quizDetail,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            onIconButtonClicked = onIconButtonClicked,
            iconContentDescription = "Back"
        )

    override fun createTakeQuizAppBar(onIconButtonClicked: (() -> Unit)?): AppBar =
        AppBar(
            titleId = StringIds.quizzes,
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            onIconButtonClicked = onIconButtonClicked,
            iconContentDescription = "Back"
        )

    override fun createQuizResultAppBar(onIconButtonClicked: (() -> Unit)?): AppBar =
        AppBar(
            titleId = StringIds.quizResult,
            imageVector = Icons.Filled.Close,
            onIconButtonClicked = onIconButtonClicked,
            iconContentDescription = "Cancel"
        )

    override fun createSpellsAppBar(): AppBar =
        AppBar(titleId = StringIds.spells, imageVector = null)

    override fun createQuizzesAppBar(): AppBar =
        AppBar(titleId = StringIds.quizzes, imageVector = null)
}