package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

/**
 * Defines UI data for the Character Detail Screen.
 * Defined and managed in the ViewModel.
 *
 * @param character The [CharacterConverter] object representing the character.
 * @param basicInfoItems The list of [CharacterDetailInfoItem] objects for basic information.
 * @param personalInfoItems The list of [CharacterDetailInfoItem] objects for personal information.
 * @param hogwartsInfoItems The list of [CharacterDetailInfoItem] objects for Hogwarts information.
 * @param additionalInfoItems The list of [CharacterDetailInfoItem] objects for additional information.
 *
 * @author Nicholas Rutherford
 */
data class CharacterDetailState(
    val character: CharacterConverter? = null,
    val basicInfoItems: List<CharacterDetailInfoItem> = emptyList(),
    val personalInfoItems: List<CharacterDetailInfoItem> = emptyList(),
    val hogwartsInfoItems: List<CharacterDetailInfoItem> = emptyList(),
    val additionalInfoItems: List<CharacterDetailInfoItem> = emptyList()
)