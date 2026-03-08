package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

/**
 * Character Detail info data class that gets managed in the viewModel
 * This represents data tied to the character in the form of a title and a value
 *
 * @param labelId The string resource ID for the label text.
 * @param value The value text to display.
 *
 * @author Nicholas Rutherford
 */
data class CharacterDetailInfoItem(
    val labelId: Int,
    val value: String
)