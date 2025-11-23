package com.nicholas.rutherford.potter.head.test

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.network.HarryPotterApiService
import com.nicholas.rutherford.potter.head.test.data.TestCharacterResponse

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

    override suspend fun fetchCharacterById(id: String): CharacterResponse {
        if (shouldThrowException) {
            throw IllegalStateException("Simulated API exception for testing error handling")
        }
        return TestCharacterResponse.build().copy(id = id)
    }
}