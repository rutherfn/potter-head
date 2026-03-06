package com.nicholas.rutherford.potter.head.database

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter

/**
 * Constants for defining default filters
 * These filters are used to initialize the database with default values since a existing filter exists
 *
 * @author Nicholas Rutherford
 */
object DefaultFilters {
    val HouseFilter = CharacterFilterConverter(
        id = 0,
        filterType = CharacterFilterType.HOUSE,
        values = listOf(
            Constants.GRYFFINDOR_HOUSE,
            Constants.RAVENCLAW_HOUSE,
            Constants.SLYTHERIN_HOUSE,
            Constants.HUFFLEPUFF_HOUSE,
            Constants.NO_HOUSE_FILTER
        ),
        isActive = true
    )

    val genderFilter = CharacterFilterConverter(
        id = 0,
        filterType = CharacterFilterType.GENDER,
        values = listOf(
            Constants.MALE,
            Constants.FEMALE
        ),
        isActive = true
    )
}