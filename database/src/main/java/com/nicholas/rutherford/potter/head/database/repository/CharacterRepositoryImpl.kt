package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.CharacterFilterExt
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter
import com.nicholas.rutherford.potter.head.database.dao.CharacterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterFilterDao
import com.nicholas.rutherford.potter.head.database.dao.CharacterImageDao
import com.nicholas.rutherford.potter.head.database.entity.CharacterEntity
import com.nicholas.rutherford.potter.head.database.entity.CharacterFilterEntity
import com.nicholas.rutherford.potter.head.database.entity.CharacterImageUrlEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

/**
 * Implementation of CharacterRepository.
 * Handles the conversion between entities and converters.
 *
 * @param dao The DAO for accessing characters.
 * @param characterFilterDao The DAO for accessing character filters.
 * @param characterImageDao The DAO for accessing character urls.
 *
 * @author Nicholas Rutherford
 */
class CharacterRepositoryImpl(
    private val dao: CharacterDao,
    private val characterFilterDao: CharacterFilterDao,
    private val characterImageDao: CharacterImageDao
) : CharacterRepository {
    private val houseUrlMap = mapOf(
        Constants.GRYFFINDOR_HOUSE.lowercase() to Constants.GRYFFINDOR_HOUSE_URL,
        Constants.RAVENCLAW_HOUSE.lowercase() to Constants.RAVENCLAW_HOUSE_URL,
        Constants.HUFFLEPUFF_HOUSE.lowercase() to Constants.HUFFLEPUFF_HOUSE_URL,
        Constants.SLYTHERIN_HOUSE.lowercase() to Constants.SLYTHERIN_HOUSE_URL
    )

    private fun buildConverterWithHouseUrl(characterConverter: CharacterConverter): CharacterConverter {
        val houseUrl = characterConverter.house?.lowercase()?.let { house -> houseUrlMap[house] }
        return houseUrl?.let { image -> characterConverter.copy(image = image) } ?: characterConverter
    }

    override suspend fun getCharacterCount(): Int = dao.getCharacterCount()

    override fun getAllCharacters(): Flow<List<CharacterConverter>> {
        val primaryFilterFlows = getPrimaryFilterFlows()
        val additionalFilterFlows = getAdditionalFilterFlows()
        
        return combine(
            dao.getAllCharacters(),
            primaryFilterFlows.houseFilterFlow,
            primaryFilterFlows.genderFilterFlow,
            primaryFilterFlows.speciesFilterFlow,
            primaryFilterFlows.hogwartsAffiliationFilterFlow
        ) { entities, houseFilters, genderFilters, speciesFilters, hogwartsAffiliationFilters ->
            val characters = convertEntitiesToConverters(entities = entities)
            val selectedValues = buildSelectedValues(
                houseFilters = houseFilters,
                genderFilters = genderFilters,
                speciesFilters = speciesFilters,
                hogwartsAffiliationFilters = hogwartsAffiliationFilters
            )
            applyPrimaryFilters(characters = characters, selectedValues = selectedValues)
        }.let { combinedFlow ->
            applyAdditionalFilters(
                combinedFlow = combinedFlow,
                wizardFilterFlow = additionalFilterFlows.wizardFilterFlow,
                aliveStatusFilterFlow = additionalFilterFlows.aliveStatusFilterFlow
            )
        }
    }


    private fun getPrimaryFilterFlows(): PrimaryFilterFlows {
        return PrimaryFilterFlows(
            houseFilterFlow = convertFilterEntitiesToConverters(
                characterFilterDao.getCharacterFiltersByType(filterType = CharacterFilterType.HOUSE)
            ),
            genderFilterFlow = convertFilterEntitiesToConverters(
                characterFilterDao.getCharacterFiltersByType(filterType = CharacterFilterType.GENDER)
            ),
            speciesFilterFlow = convertFilterEntitiesToConverters(
                characterFilterDao.getCharacterFiltersByType(filterType = CharacterFilterType.SPECIES)
            ),
            hogwartsAffiliationFilterFlow = convertFilterEntitiesToConverters(
                characterFilterDao.getCharacterFiltersByType(filterType = CharacterFilterType.HOGWARTS_AFFILIATION)
            )
        )
    }

    private fun getAdditionalFilterFlows(): AdditionalFilterFlows {
        return AdditionalFilterFlows(
            wizardFilterFlow = convertFilterEntitiesToConverters(
                characterFilterDao.getCharacterFiltersByType(filterType = CharacterFilterType.WIZARD_STATUS)
            ),
            aliveStatusFilterFlow = convertFilterEntitiesToConverters(
                characterFilterDao.getCharacterFiltersByType(filterType = CharacterFilterType.ALIVE_STATUS)
            )
        )
    }

    private fun convertFilterEntitiesToConverters(
        entitiesFlow: Flow<List<CharacterFilterEntity>>
    ): Flow<List<CharacterFilterConverter>> {
        return entitiesFlow.map { entities ->
            entities.map { entity -> CharacterFilterConverter.fromEntity(entity = entity) }
        }
    }


    private fun buildSelectedValues(
        houseFilters: List<CharacterFilterConverter>,
        genderFilters: List<CharacterFilterConverter>,
        speciesFilters: List<CharacterFilterConverter>,
        hogwartsAffiliationFilters: List<CharacterFilterConverter>
    ): SelectedValues {
        return SelectedValues(
            houseValues = houseFilters.firstOrNull()?.values ?: emptyList(),
            genderValues = genderFilters.firstOrNull()?.values ?: emptyList(),
            speciesValues = speciesFilters.firstOrNull()?.values ?: emptyList(),
            hogwartsAffiliationValues = hogwartsAffiliationFilters.firstOrNull()?.values ?: emptyList()
        )
    }

    private fun convertEntitiesToConverters(entities: List<CharacterEntity>): List<CharacterConverter> {
        return entities.map { characterEntity -> CharacterConverter.fromEntity(entity = characterEntity) }
    }

    private fun applyPrimaryFilters(
        characters: List<CharacterConverter>,
        selectedValues: SelectedValues
    ): List<CharacterConverter> {
        val filteredByHouse = CharacterFilterExt.filterCharactersByHouse(characters = characters, selectedHouseValues = selectedValues.houseValues)
        val filteredByGender = CharacterFilterExt.filterCharactersByGender(characters = filteredByHouse, selectedGenderValues = selectedValues.genderValues)
        val filteredBySpecies = CharacterFilterExt.filterCharactersBySpecies(characters = filteredByGender, selectedSpeciesValues = selectedValues.speciesValues)
        return CharacterFilterExt.filterCharactersByHouseAffiliation(
            characters = filteredBySpecies,
            selectedAffiliationsValues = selectedValues.hogwartsAffiliationValues
        )
    }

    private fun applyAdditionalFilters(
        combinedFlow: Flow<List<CharacterConverter>>,
        wizardFilterFlow: Flow<List<CharacterFilterConverter>>,
        aliveStatusFilterFlow: Flow<List<CharacterFilterConverter>>
    ): Flow<List<CharacterConverter>> {
        return combine(combinedFlow, wizardFilterFlow) { filteredCharacters, wizardFilters ->
            val selectedWizardValues = wizardFilters.firstOrNull()?.values ?: emptyList()
            CharacterFilterExt.filterCharactersByWizard(characters = filteredCharacters, selectedWizardValues = selectedWizardValues)
        }.let { combinedFlow ->
            combine(combinedFlow, aliveStatusFilterFlow) { filteredCharacters, aliveStatusFilters ->
                val selectedAliveStatusValues = aliveStatusFilters.firstOrNull()?.values ?: emptyList()
                CharacterFilterExt.filterCharactersByAliveStatus(characters = filteredCharacters, selectedAliveStatusValues = selectedAliveStatusValues)
            }
        }
    }

    override fun getCharacterByName(name: String): Flow<CharacterConverter> {
        return dao.getCharacterByName(name).map { entity ->
            CharacterConverter.fromEntity(entity = entity)
        }
    }

    override suspend fun insertCharacter(character: CharacterConverter) = dao.insertCharacter(character = character.toEntity())

    override suspend fun insertAllCharacters(characters: List<CharacterConverter>) {
        val imageUrlMap = getCharacterImageUrlMap()
        val charactersWithMergedUrls = mergeImageUrlsWithCharacters(characters = characters, imageUrlMap = imageUrlMap)
        dao.insertAllCharacters(characters = charactersWithMergedUrls.map { it.toEntity() })
    }

    private suspend fun getCharacterImageUrlMap(): Map<String, CharacterImageUrlEntity> {
        return characterImageDao.getAllCharacterImageUrlsSync()
            .associateBy { entity -> entity.characterName.trim().lowercase() }
    }

    private fun mergeImageUrlsWithCharacters(
        characters: List<CharacterConverter>,
        imageUrlMap: Map<String, CharacterImageUrlEntity>
    ): List<CharacterConverter> {
        return characters.map { character -> character.mergeImageUrlIfNeeded(imageUrlMap = imageUrlMap) }
    }

    override suspend fun searchCharacters(query: String): List<CharacterConverter> {
        val houseFilters = characterFilterDao.getCharacterFiltersByTypeSync(filterType = CharacterFilterType.HOUSE)
        val genderFilters = characterFilterDao.getCharacterFiltersByTypeSync(filterType = CharacterFilterType.GENDER)
        val speciesFilters = characterFilterDao.getCharacterFiltersByTypeSync(filterType = CharacterFilterType.SPECIES)
        val hogwartsAffiliationFilters = characterFilterDao.getCharacterFiltersByTypeSync(filterType = CharacterFilterType.HOGWARTS_AFFILIATION)
        val wizardFilters = characterFilterDao.getCharacterFiltersByTypeSync(filterType = CharacterFilterType.WIZARD_STATUS)
        val aliveStatusFilters = characterFilterDao.getCharacterFiltersByTypeSync(filterType = CharacterFilterType.ALIVE_STATUS)

        val selectedHouseValues = houseFilters.firstOrNull()?.values ?: emptyList()
        val selectedGenderValues = genderFilters.firstOrNull()?.values ?: emptyList()
        val selectedSpeciesValues = speciesFilters.firstOrNull()?.values ?: emptyList()
        val selectedHogwartsAffiliationValues = hogwartsAffiliationFilters.firstOrNull()?.values ?: emptyList()
        val selectedWizardValues = wizardFilters.firstOrNull()?.values ?: emptyList()
        val selectedAliveStatusValues = aliveStatusFilters.firstOrNull()?.values ?: emptyList()
        
        val characters = dao.searchCharacter(query = query).map { entity -> 
            CharacterConverter.fromEntity(entity = entity) 
        }
        
        val filteredByHouse = CharacterFilterExt.filterCharactersByHouse(characters = characters, selectedHouseValues = selectedHouseValues)
        val filteredByGender = CharacterFilterExt.filterCharactersByGender(characters = filteredByHouse, selectedGenderValues = selectedGenderValues)
        val filteredBySpecies = CharacterFilterExt.filterCharactersBySpecies(characters = filteredByGender, selectedSpeciesValues = selectedSpeciesValues)
        val filteredByHogwartsAffiliation = CharacterFilterExt.filterCharactersByHouseAffiliation(characters = filteredBySpecies, selectedAffiliationsValues = selectedHogwartsAffiliationValues)
        val filteredByWizard = CharacterFilterExt.filterCharactersByWizard(characters = filteredByHogwartsAffiliation, selectedWizardValues = selectedWizardValues)
        return CharacterFilterExt.filterCharactersByAliveStatus(characters = filteredByWizard, selectedAliveStatusValues = selectedAliveStatusValues)
    }

    private fun CharacterConverter.mergeImageUrlIfNeeded(imageUrlMap: Map<String, CharacterImageUrlEntity>): CharacterConverter {
        return if (image.isNullOrBlank()) {
            imageUrlMap[name.trim().lowercase()]?.let { imageUrl ->
                copy(image = imageUrl.imageUrl)
            } ?: buildConverterWithHouseUrl(characterConverter = this)
        } else {
            this
        }
    }

    override suspend fun updateCharacter(character: CharacterConverter) = dao.updateCharacter(character = character.toEntity())

    override suspend fun deleteCharacterByName(name: String) = dao.deleteCharacterByName(name = name)

    override suspend fun deleteAllCharacters() = dao.deleteAllCharacters()
}