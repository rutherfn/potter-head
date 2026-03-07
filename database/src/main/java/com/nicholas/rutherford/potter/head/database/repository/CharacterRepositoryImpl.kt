package com.nicholas.rutherford.potter.head.database.repository

import com.nicholas.rutherford.potter.head.core.Constants
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


    private fun filterCharactersByHouse(
        characters: List<CharacterConverter>,
        selectedHouseValues: List<String>
    ): List<CharacterConverter> {
        val normalizedSelectedHouses = selectedHouseValues
            .map { it.trim().lowercase() }
            .filter { it != Constants.NO_HOUSE_FILTER.lowercase() }
            .toSet()
        val includeNoHouse = selectedHouseValues.any { it.trim().lowercase() == Constants.NO_HOUSE_FILTER.lowercase() }

        if (normalizedSelectedHouses.isEmpty()) {
            return characters.filter { character ->
                character.house.isNullOrBlank()
            }
        }

        return characters.filter { character ->
            val house = character.house?.trim()?.lowercase()
            val hasSelectedHouse = house != null && normalizedSelectedHouses.contains(house)
            val hasNoHouse = character.house.isNullOrBlank()
            
            hasSelectedHouse || (includeNoHouse && hasNoHouse)
        }
    }

    private fun filterCharactersByHouseAffiliation(
        characters: List<CharacterConverter>,
        selectedAffiliationsValues: List<String>
    ): List<CharacterConverter> {
        if (selectedAffiliationsValues.isEmpty()) {
            return emptyList()
        } else {
            val normalizedSelectedAffiliations = selectedAffiliationsValues
                .map { it.trim().lowercase() }
                .toSet()

            return characters.filter { character ->
                val hasHouseAffiliation = character.isHogwartsStudent || character.isHogwartsStaff
                val affiliationValue = if (hasHouseAffiliation) {
                    Constants.HAS_HOUSE_AFFILIATION_FILTER.lowercase()
                } else {
                    Constants.HAS_NOT_HOUSE_AFFILIATION_FILTER.lowercase()
                }
                normalizedSelectedAffiliations.contains(affiliationValue)
            }
        }
    }

    private fun filterCharactersByGender(
        characters: List<CharacterConverter>,
        selectedGenderValues: List<String>
    ): List<CharacterConverter> {
        if (selectedGenderValues.isEmpty()) {
            return emptyList()
        } else {
            val normalizedSelectedGenders = selectedGenderValues
                .map { it.trim().lowercase() }
                .toSet()

            return characters.filter { character ->
                val gender = character.gender.trim().lowercase()
                gender.isNotEmpty() && normalizedSelectedGenders.contains(gender)
            }
        }
    }

    private fun filterCharactersBySpecies(
        characters: List<CharacterConverter>,
        selectedSpeciesValues: List<String>
    ): List<CharacterConverter> {
        if (selectedSpeciesValues.isEmpty()) {
            return emptyList()
        } else {
            val normalizedSelectedSpecies = selectedSpeciesValues
                .map { it.trim().lowercase() }
                .toSet()

            return characters.filter { character ->
                val species = character.species.trim().lowercase()
                species.isNotEmpty() && normalizedSelectedSpecies.contains(species)
            }
        }
    }

    private fun filterCharactersByWizard(
        characters: List<CharacterConverter>,
        selectedWizardValues: List<String>
    ): List<CharacterConverter> {
        if (selectedWizardValues.isEmpty()) {
            return emptyList()
        } else {
            val normalizedSelectedWizard = selectedWizardValues
                .map { it.trim().lowercase() }
                .toSet()

            return characters.filter { character ->
                val wizardValue = if (character.isWizard) {
                    Constants.IS_WIZARD_FILTER.lowercase()
                } else {
                    Constants.IS_NOT_WIZARD_FILTER.lowercase()
                }
                normalizedSelectedWizard.contains(wizardValue)
            }
        }
    }

    private fun filterCharactersByAliveStatus(
        characters: List<CharacterConverter>,
        selectedAliveStatusValues: List<String>
    ): List<CharacterConverter> {
        if (selectedAliveStatusValues.isEmpty()) {
            return emptyList()
        } else {
            val normalizedSelectedAliveStatus = selectedAliveStatusValues
                .map { it.trim().lowercase() }
                .toSet()

            return characters.filter { character ->
                val aliveStatusValue = if (character.isAlive) {
                    Constants.IS_ALIVE_FILTER.lowercase()
                } else {
                    Constants.IS_NOT_ALIVE_FILTER.lowercase()
                }
                normalizedSelectedAliveStatus.contains(aliveStatusValue)
            }
        }
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
        val filteredByHouse = filterCharactersByHouse(characters = characters, selectedHouseValues = selectedValues.houseValues)
        val filteredByGender = filterCharactersByGender(characters = filteredByHouse, selectedGenderValues = selectedValues.genderValues)
        val filteredBySpecies = filterCharactersBySpecies(characters = filteredByGender, selectedSpeciesValues = selectedValues.speciesValues)
        return filterCharactersByHouseAffiliation(
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
            filterCharactersByWizard(characters = filteredCharacters, selectedWizardValues = selectedWizardValues)
        }.let { combinedFlow ->
            combine(combinedFlow, aliveStatusFilterFlow) { filteredCharacters, aliveStatusFilters ->
                val selectedAliveStatusValues = aliveStatusFilters.firstOrNull()?.values ?: emptyList()
                filterCharactersByAliveStatus(characters = filteredCharacters, selectedAliveStatusValues = selectedAliveStatusValues)
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
        
        val filteredByHouse = filterCharactersByHouse(characters = characters, selectedHouseValues = selectedHouseValues)
        val filteredByGender = filterCharactersByGender(characters = filteredByHouse, selectedGenderValues = selectedGenderValues)
        val filteredBySpecies = filterCharactersBySpecies(characters = filteredByGender, selectedSpeciesValues = selectedSpeciesValues)
        val filteredByHogwartsAffiliation = filterCharactersByHouseAffiliation(characters = filteredBySpecies, selectedAffiliationsValues = selectedHogwartsAffiliationValues)
        val filteredByWizard = filterCharactersByWizard(characters = filteredByHogwartsAffiliation, selectedWizardValues = selectedWizardValues)
        return filterCharactersByAliveStatus(characters = filteredByWizard, selectedAliveStatusValues = selectedAliveStatusValues)
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