package com.nicholas.rutherford.potter.head.network

import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.test.TestHarryPotterApiService
import com.nicholas.rutherford.potter.head.test.shouldBe
import com.nicholas.rutherford.potter.head.test.shouldBeInstanceOf
import com.nicholas.rutherford.potter.head.test.shouldNotBe
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class HarryPotterApiRepositoryImplTest {

    @Nested
    inner class GetAllCharacters {

        @Test
        fun `should return success result when API service returns characters`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = true)
            val repository = HarryPotterApiRepositoryImpl(apiService = apiService)

            val results = repository.getAllCharacters().toList()

            results shouldNotBe emptyList()

            val result = results.first()

            result.isSuccess shouldBe true
            result.isFailure shouldBe false
            result.getOrNull() shouldNotBe null

            val characters = result.getOrNull()

            characters shouldNotBe null
            characters!!.isNotEmpty() shouldBe true

            val exception = result.exceptionOrNull()

            exception shouldBe null
        }

        @Test
        fun `should return success result when API service returns empty list`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = false)
            val repository = HarryPotterApiRepositoryImpl(apiService = apiService)

            val results = repository.getAllCharacters().toList()

            results.isNotEmpty() shouldBe true

            val result = results.first()

            result.isSuccess shouldBe true
            result.isFailure shouldBe false
            result.getOrNull() shouldNotBe null

            val characters = result.getOrNull()

            characters shouldNotBe null
            characters!!.isEmpty() shouldBe true

            val exception = result.exceptionOrNull()

            exception shouldBe null
        }

        @Test
        fun `should return failure result when API service throws exception`() = runTest {
            val apiService = object : HarryPotterApiService {
                override suspend fun fetchAllCharacters(): List<CharacterResponse> { throw IllegalStateException("Simulated API exception for testing error handling") }
                override suspend fun fetchCharacterById(id: String): CharacterResponse { throw IllegalStateException("Simulated API exception for testing error handling") }
            }
            val repository = HarryPotterApiRepositoryImpl(apiService = apiService)

            val results = repository.getAllCharacters().toList()

            results.isNotEmpty() shouldBe true

            val result = results.first()

            result.isFailure shouldBe true
            result.isSuccess shouldBe false
            result.getOrNull() shouldBe null

            val exception = result.exceptionOrNull()

            exception shouldNotBe null
            exception.shouldBeInstanceOf<IllegalStateException>()
            exception!!.message shouldBe "Simulated API exception for testing error handling"
        }
    }

    @Nested
    inner class GetCharacterById {

        @Test
        fun `should return success result when API service returns character`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = true)
            val repository = HarryPotterApiRepositoryImpl(apiService = apiService)
            val id = "test-character-id"

            val results = repository.getCharacterById(id = id).toList()

            results.isNotEmpty() shouldBe true

            val result = results.first()

            result.isSuccess shouldBe true
            result.isFailure shouldBe false

            val character = result.getOrNull()

            character shouldNotBe null
            character!!.id shouldBe id
        }

        @Test
        fun `should return failure result when API service throws exception`() = runTest {
            val apiService = TestHarryPotterApiService(shouldReturnData = true, shouldThrowException = true)
            val repository = HarryPotterApiRepositoryImpl(apiService = apiService)
            val id = "test-character-id"

            val results = repository.getCharacterById(id = id).toList()

            results.isNotEmpty() shouldBe true

            val result = results.first()

            result.isFailure shouldBe true
            result.isSuccess shouldBe false

            val exception = result.exceptionOrNull()

            exception shouldNotBe null
            exception.shouldBeInstanceOf<IllegalStateException>()
            exception?.message shouldBe "Simulated API exception for testing error handling"
        }
    }
}
