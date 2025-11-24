package com.nicholas.rutherford.potter.head.network

import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Full implementation of the [HarryPotterApiRepository] interface
 *
 * @author Nicholas Rutherford
 */
class HarryPotterApiRepositoryImpl(
    private val apiService: HarryPotterApiService
) : HarryPotterApiRepository {

    /**
     * Kermit Logger for this class.
     */
    private val log = Logger.withTag(tag = "HarryPotterApiRepositoryImpl")

    /**
     * Fetches all characters from the API.
     *
     * @return A [Flow] emitting a [Result] containing a list of [CharacterResponse] objects.
     */
    override fun getAllCharacters(): Flow<Result<List<CharacterResponse>>> = flow {
        try {
            val characters = apiService.fetchAllCharacters()
            emit(value = Result.success(value = characters))
            log.i("Was able to successfully fetch all characters by list with following being the first character -- ${characters.first()}")
        } catch (exception: Exception) {
            emit(value = Result.failure(exception = exception))
            log.e("Was not able to successfully fetch all characters by list with a exception message -- ${exception.message}")
        }
    }

    /**
     * Fetches a character by the id from the API
     *
     * @param id The ID of the character to fetch.
     *
     */
    override fun getCharacterById(id: String): Flow<Result<List<CharacterResponse>>> = flow {
        try {
            log.d("Attempting to fetch character with id: $id")
            println("DEBUG Repository: Fetching character with id: $id")
            val character = apiService.fetchCharacterById(id = id)
            emit(value = Result.success(value = character))
            log.i("Was able to successfully fetch character with id $id with following being the character that was fetched -- $character")
        } catch (exception: Exception) {
            emit(value = Result.failure(exception = exception))
            log.e("Was not able to successfully fetch character with id $id with a exception message -- ${exception.message}")
            println("DEBUG Repository: Failed to fetch character with id: $id, error: ${exception.message}")
        }
    }

}