package com.nicholas.rutherford.potter.head.test.utils.api

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.model.network.SpellResponse
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import com.nicholas.rutherford.potter.head.test.utils.data.TestCharacterResponse

/**
 * Test implementation of [HarryPotterApiService] for use in unit tests.
 *
 * @param shouldReturnData If `true`, returns test data; if `false`, returns an empty list.
 *                        Defaults to `true`.
 *
 * @see HarryPotterApiService for the interface definition
 * @see TestCharacterResponse for the test data used by this service
 *
 * @author Nicholas Rutherford
 */
class TestHarryPotterApiService(
    private val shouldReturnData: Boolean = true
) : HarryPotterApiService {

    override suspend fun fetchAllCharacters(): List<CharacterResponse> {
        return if (shouldReturnData) {
            TestCharacterResponse.buildVariedList()
        } else {
            emptyList()
        }
    }

    override suspend fun fetchAllSpells(): List<SpellResponse> {
        return emptyList()
    }
}
