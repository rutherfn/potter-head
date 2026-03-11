package com.nicholas.rutherford.potter.head.feature.spells

import com.nicholas.rutherford.potter.head.core.StringIds

/**
 * Defines different error types you can get when fetching spells.
 *
 * @param titleId The string resource ID for the error title.
 * @param descriptionId The string resource ID for the error description.
 *
 * @author Nicholas Rutherford
 */
enum class SpellsErrorType(val titleId: Int?, val descriptionId: Int?) {
    NO_INTERNET_CONNECTION(titleId = StringIds.noInternetConnection, descriptionId = StringIds.unableToConnectToInternetPleaseCheckYourInternetConnection),
    FAILED_TO_FETCH_SPELLS(titleId = StringIds.couldNotRetrieveSpells, descriptionId = StringIds.wasUnableToRetrieveTheSpellsPleaseTryAgainLater),
    NONE(titleId = null, descriptionId = null)
}

fun SpellsErrorType.isValidErrorType(): Boolean = this == SpellsErrorType.FAILED_TO_FETCH_SPELLS || this == SpellsErrorType.NO_INTERNET_CONNECTION

