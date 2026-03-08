package com.nicholas.rutherford.potter.head.test.utils

import com.nicholas.rutherford.potter.head.test.utils.api.TestHarryPotterApiService
import com.nicholas.rutherford.potter.head.test.utils.data.TestCharacterResponse
import com.nicholas.rutherford.potter.head.test.utils.ext.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class TestHarryPotterApiServiceTest {

    @Nested
    inner class FetchAllCharacters {

        @Test
        fun `should return varied list of characters when shouldReturnData is true`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = true)

            val characters = apiService.fetchAllCharacters()

            characters.isEmpty() shouldBe false
            characters.size shouldBe 5
            characters shouldBe TestCharacterResponse.buildVariedList()
        }

        @Test
        fun `should return empty list when shouldReturnData is false`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = false)

            val characters = apiService.fetchAllCharacters()

            characters.isEmpty() shouldBe true
            characters.size shouldBe 0
            characters shouldBe emptyList()
        }
    }
}

