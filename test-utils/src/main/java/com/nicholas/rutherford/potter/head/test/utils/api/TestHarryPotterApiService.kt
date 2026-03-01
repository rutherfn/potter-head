package com.nicholas.rutherford.potter.head.test.utils.api

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import com.nicholas.rutherford.potter.head.test.utils.data.TestCharacterResponse

/**
 * Test implementation of [HarryPotterApiService] for use in unit tests.
 *
 * This class provides a controllable implementation of the API service that allows
 * testing different scenarios without making actual network calls. It provides the following:
 * - Configurable data return behavior
 * - Configurable exception throwing for error handling tests
 * - Uses [TestCharacterResponse] for generating test data
 *
 * @param shouldReturnData If `true`, returns test data; if `false`, returns an empty list.
 *                        Defaults to `true`.
 * @param shouldThrowException If `true`, throws an exception when fetching by ID.
 *                             Defaults to `false`.
 *
 * @see HarryPotterApiService for the interface definition
 * @see TestCharacterResponse for the test data used by this service
 *
 * @author Nicholas Rutherford
 */
class TestHarryPotterApiService(
    private val shouldReturnData: Boolean = true,
    private val shouldThrowException: Boolean = false
) : HarryPotterApiService {

    override suspend fun fetchAllCharacters(): List<CharacterResponse> {
        return if (shouldReturnData) {
            TestCharacterResponse.buildVariedList()
        } else {
            emptyList()
        }
    }

    override suspend fun fetchCharacterById(id: String): List<CharacterResponse> {
        if (shouldThrowException) {
            throw IllegalStateException("Simulated API exception for testing error handling")
        }
        return listOf(TestCharacterResponse.build().copy(id = id))
    }
}