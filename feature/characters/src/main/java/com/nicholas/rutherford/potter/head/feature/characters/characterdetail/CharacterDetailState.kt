package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

data class CharacterDetailState(
    val character: CharacterConverter? = null,
    val basicInfoItems: List<CharacterDetailInfoItem> = emptyList(),
    val personalInfoItems: List<CharacterDetailInfoItem> = emptyList(),
    val hogwartsInfoItems: List<CharacterDetailInfoItem> = emptyList(),
    val additionalInfoItems: List<CharacterDetailInfoItem> = emptyList()
)