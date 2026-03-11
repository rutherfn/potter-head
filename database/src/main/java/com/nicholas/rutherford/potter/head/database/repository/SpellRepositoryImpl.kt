package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.database.converter.SpellConverter
import com.nicholas.rutherford.potter.head.database.dao.SpellDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * Implementation of SpellRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing spells.
 *
 * @author Nicholas Rutherford
 */
class SpellRepositoryImpl(private val dao: SpellDao) : SpellRepository {

    override suspend fun getSpellCount(): Int = dao.getSpellCount()

    override fun getAllSpells(): Flow<List<SpellConverter>> {
        return dao.getAllSpells().map { entities ->
            entities.map { entity -> SpellConverter.fromEntity(entity = entity) }
        }
    }

    override fun getSpellByName(name: String): Flow<SpellConverter> {
        return dao.getSpellByName(name).map { entity ->
            SpellConverter.fromEntity(entity = entity)
        }
    }

    override suspend fun insertSpell(spell: SpellConverter) {
        dao.insertAllSpells(spells = listOf(spell.toEntity()))
    }

    override suspend fun insertAllSpells(spells: List<SpellConverter>) {
        dao.insertAllSpells(spells = spells.map { spell -> spell.toEntity() })
    }

    override suspend fun searchSpells(query: String): List<SpellConverter> {
        return dao.searchSpell(query = query).map { entity ->
            SpellConverter.fromEntity(entity = entity)
        }
    }

    override suspend fun updateSpell(spell: SpellConverter) {
        dao.updateSpell(spellEntity = spell.toEntity())
    }

    override suspend fun deleteSpellByName(name: String) {
        dao.deleteSpellByName(name = name)
    }

    override suspend fun deleteAllSpells() {
        dao.deleteAllSpells()
    }
}