package com.nicholas.rutherford.potter.head.network

import co.touchlab.kermit.Logger
import com.nicholas.rutherford.potter.head.model.network.CharacterResponse
import com.nicholas.rutherford.potter.head.model.network.SpellResponse
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
        log.i(messageString = "Fetched ${characters.size} characters from API")
        emit(value = Result.success(value = characters))
    }.catch { exception ->
        log.e("Was not able to successfully fetch all characters by list with a exception message -- ${exception.message}")
        emit(value = Result.failure(exception = exception))
    }

    override fun getAllSpells(): Flow<Result<List<SpellResponse>>> = flow {
        val spells = apiService.fetchAllSpells()
        log.i(messageString = "Fetched ${spells.size} spells from API")
        emit(value = Result.success(value = spells))
    }.catch { exception ->
        log.e("Was not able to successfully fetch all spells by list with a exception message -- ${exception.message}")
        emit(value = Result.failure(exception = exception))
    }
}
