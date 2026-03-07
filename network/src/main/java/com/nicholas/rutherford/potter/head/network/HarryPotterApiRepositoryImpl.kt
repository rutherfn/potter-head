package com.nicholas.rutherford.potter.head.network

import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

/**
 * Full implementation of the [HarryPotterApiRepository] interface
 *
 * @author Nicholas Rutherford
 */
class HarryPotterApiRepositoryImpl(
    private val apiService: HarryPotterApiService
) : HarryPotterApiRepository {
    private val log = Logger.withTag(tag = "HarryPotterApiRepositoryImpl")

    override fun getAllCharacters(): Flow<Result<List<CharacterResponse>>> = flow {
        val characters = apiService.fetchAllCharacters()
        log.i("Fetched ${characters.size} characters from API")
        emit(value = Result.success(value = characters))
    }.catch { exception ->
        log.e("Was not able to successfully fetch all characters by list with a exception message -- ${exception.message}")
        emit(value = Result.failure(exception = exception))
    }

    override fun getCharacterById(id: String): Flow<Result<List<CharacterResponse>>> = flow {
        log.d("Attempting to fetch character with id: $id")
        val character = apiService.fetchCharacterById(id = id)
        log.i("Was able to successfully fetch character with id $id with following being the character that was fetched -- $character")
        emit(value = Result.success(value = character))
    }.catch { exception ->
        log.e("Was not able to successfully fetch character with id $id with a exception message -- ${exception.message}")
        emit(value = Result.failure(exception = exception))
    }
}