package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
}