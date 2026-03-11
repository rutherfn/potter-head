package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.SpellConverter
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing spells.
 * Provides a clean way to access and modify cached spells data in the database.
 *
 * @author Nicholas Rutherford
 */
interface SpellRepository {
    suspend fun getSpellCount(): Int

    fun getAllSpells(): Flow<List<SpellConverter>>

    fun getSpellByName(name: String): Flow<SpellConverter>

    suspend fun insertSpell(spell: SpellConverter)

    suspend fun insertAllSpells(spells: List<SpellConverter>)

    suspend fun searchSpells(query: String): List<SpellConverter>

    suspend fun updateSpell(spell: SpellConverter)

    suspend fun deleteSpellByName(name: String)

    suspend fun deleteAllSpells()
}