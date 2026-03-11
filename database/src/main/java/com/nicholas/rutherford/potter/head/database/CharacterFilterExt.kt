package com.nicholas.rutherford.potter.head.database

import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import com.nicholas.rutherford.potter.head.database.repository.CharacterRepositoryImpl

/**
 * Collection of filter functions that is directly used in [CharacterRepositoryImpl]
 *
 * @author Nicholas Rutherford
 */
object CharacterFilterExt {

    fun filterCharactersByHouse(
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

    fun filterCharactersByHouseAffiliation(
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

    fun filterCharactersByGender(
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

    fun filterCharactersBySpecies(
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

    fun filterCharactersByWizard(
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

    fun filterCharactersByAliveStatus(
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
}