package com.nicholas.rutherford.potter.head.feature.characters.characters

import com.nicholas.rutherford.potter.head.core.StringIds

enum class CharactersErrorType(val titleId: Int?, val descriptionId: Int?, ) {
    NO_INTERNET_CONNECTION(titleId = StringIds.noInternetConnection, descriptionId = StringIds.unableToConnectToInternetPleaseCheckYourInternetConnection),
    FAILED_TO_FETCH_CHARACTERS(titleId= StringIds.couldNotRetrieveCharacters, descriptionId = StringIds.wasUnableToRetrieveTheCharactersPlease_try_again_later),
    NONE(titleId = null, descriptionId = null)
}

fun CharactersErrorType.isValidErrorType(): Boolean = this == CharactersErrorType.FAILED_TO_FETCH_CHARACTERS || this == CharactersErrorType.NO_INTERNET_CONNECTION