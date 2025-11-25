package com.nicholas.rutherford.potter.head.test.utils

import com.nicholas.rutherford.potter.head.test.utils.data.TestCharacterResponse
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

    @Nested
    inner class FetchCharacterById {

        @Test
        fun `should return character with given id when shouldReturnData is true and shouldThrowException is false`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = true, shouldThrowException = false)
            val testId = "custom-character-id"
            val characters = apiService.fetchCharacterById(id = testId)

            characters.isNotEmpty() shouldBe true
            val character = characters.first()
            character.id shouldBe testId
            character.name shouldBe "Harry Potter"
            character.house shouldBe "Gryffindor"
        }

        @Test
        fun `should throw IllegalStateException when shouldThrowException is true`() = runTest {
            val apiService = TestHarryPotterApiService(
                shouldReturnData = true,
                shouldThrowException = true
            )
            val testId = "test-id"

            try {
                apiService.fetchCharacterById(id = testId)
                throw AssertionError("Expected IllegalStateException to be thrown")
            } catch (exception: IllegalStateException) {
                exception.shouldBeInstanceOf<IllegalStateException>()
                exception.message shouldBe "Simulated API exception for testing error handling"
            }
        }

        @Test
        fun `should return character when shouldReturnData is false but shouldThrowException is false`() = runTest {
            val apiService = TestHarryPotterApiService(
                shouldReturnData = false,
                shouldThrowException = false
            )
            val testId = "test-id"

            val characters = apiService.fetchCharacterById(id = testId)

            characters.isNotEmpty() shouldBe true
            val character = characters.first()
            character.id shouldBe testId
            character.name shouldBe "Harry Potter"
        }
    }
}

