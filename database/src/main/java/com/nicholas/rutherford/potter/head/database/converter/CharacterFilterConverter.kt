package com.nicholas.rutherford.potter.head.database.converter

import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.entity.CharacterFilterEntity

data class CharacterFilterConverter(
    val id: Int,
    val filterType: CharacterFilterType,
    val values: List<String>,
    val isActive: Boolean
) {

    fun toEntity(): CharacterFilterEntity = CharacterFilterEntity(
        id = id,
        filterType = filterType,
        values = values,
        isActive = isActive
    )

    companion object {

        fun fromEntity(entity: CharacterFilterEntity): CharacterFilterConverter =
            CharacterFilterConverter(
                id = entity.id,
                filterType = entity.filterType,
                values = entity.values,
                isActive = entity.isActive
            )
    }
}