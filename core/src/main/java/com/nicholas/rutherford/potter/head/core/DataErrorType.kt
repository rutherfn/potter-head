package com.nicholas.rutherford.potter.head.core

/**
 * Defines different error types you can get when fetching data.
 *
 * @param titleId The string resource ID for the error title.
 * @param descriptionId The string resource ID for the error description.
 *
 * @author Nicholas Rutherford
 */
sealed class DataErrorType(
    val titleId: Int?,
    val descriptionId: Int?
) {

    /**
     * Error type for when there is no internet connection.
     */
    object NoInternetConnection : DataErrorType(
        titleId = StringIds.noInternetConnection,
        descriptionId = StringIds.unableToConnectToInternetPleaseCheckYourInternetConnection
    )

    /**
     * Error type for when data fetching fails.
     *
     * @param dataType The type of data that failed to fetch (e.g., "Characters", "Spells", "Quizzes").
     */
    data class FailedToFetchData(val dataType: String) : DataErrorType(
        titleId = StringIds.couldNotRetrieveData,
        descriptionId = StringIds.wasUnableToRetrieveTheDataPleaseTryAgainLater
    )

    /**
     * No error state.
     */
    object None : DataErrorType(
        titleId = null,
        descriptionId = null
    )
}

/**
 * Extension function to check if the error type is a valid error (not NONE).
 */
fun DataErrorType.isValidErrorType(): Boolean = this is DataErrorType.NoInternetConnection || this is DataErrorType.FailedToFetchData
