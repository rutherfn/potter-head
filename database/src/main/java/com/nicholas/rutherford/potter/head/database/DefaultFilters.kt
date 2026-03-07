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

    val speciesFilter = CharacterFilterConverter(
        id = 0,
        filterType = CharacterFilterType.SPECIES,
        values = listOf(
            Constants.SPECIES_ACROMANTULA,
            Constants.SPECIES_CAT,
            Constants.SPECIES_CENTAUR,
            Constants.SPECIES_CEPHALOPOD,
            Constants.SPECIES_DOG,
            Constants.SPECIES_DRAGON,
            Constants.SPECIES_GHOST,
            Constants.SPECIES_GIANT,
            Constants.SPECIES_GOBLIN,
            Constants.SPECIES_HALF_GIANT,
            Constants.SPECIES_HALF_HUMAN,
            Constants.SPECIES_HAT,
            Constants.SPECIES_HIPPOGRIFF,
            Constants.SPECIES_HOUSE_ELF,
            Constants.SPECIES_HUMAN,
            Constants.SPECIES_OWL,
            Constants.SPECIES_PHOENIX,
            Constants.SPECIES_POLTERGEIST,
            Constants.SPECIES_PYGMY_PUFF,
            Constants.SPECIES_SELKIE,
            Constants.SPECIES_SERPENT,
            Constants.SPECIES_SNAKE,
            Constants.SPECIES_THREE_HEADED_DOG,
            Constants.SPECIES_TOAD,
            Constants.SPECIES_VAMPIRE,
            Constants.SPECIES_WEREWOLF
        ),
        isActive = true
    )

    val hogwartsAffiliationFilter = CharacterFilterConverter(
        id = 0,
        filterType = CharacterFilterType.HOGWARTS_AFFILIATION,
        values = listOf(
            Constants.HAS_HOUSE_AFFILIATION_FILTER,
            Constants.HAS_NOT_HOUSE_AFFILIATION_FILTER
        ),
        isActive = true
    )

    val isWizardFilter = CharacterFilterConverter(
        id = 0,
        filterType = CharacterFilterType.WIZARD_STATUS,
        values = listOf(
            Constants.IS_WIZARD_FILTER,
            Constants.IS_NOT_WIZARD_FILTER
        ),
        isActive = true
    )

    val isAliveFilter = CharacterFilterConverter(
        id = 0,
        filterType = CharacterFilterType.ALIVE_STATUS,
        values = listOf(
            Constants.IS_ALIVE_FILTER,
            Constants.IS_NOT_ALIVE_FILTER
        ),
        isActive = true
    )
}