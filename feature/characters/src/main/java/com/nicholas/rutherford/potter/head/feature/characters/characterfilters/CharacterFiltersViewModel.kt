package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.CharacterFilterType
import com.nicholas.rutherford.potter.head.database.converter.CharacterFilterConverter
import com.nicholas.rutherford.potter.head.database.repository.CharacterFilterRepository
import com.nicholas.rutherford.potter.head.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CharacterFiltersViewModel(
    private val characterFilterRepository: CharacterFilterRepository,
    private val navigator: Navigator
) : BaseViewModel() {

    private val characterFiltersMutableStateFlow = MutableStateFlow(CharacterFiltersState())
    val characterFiltersStateFlow: StateFlow<CharacterFiltersState> = characterFiltersMutableStateFlow.asStateFlow()

    internal var houseFilterValues: List<String> = emptyList()
    internal var genderFilterValues: List<String> = emptyList()


    init {
        collectCharacterFilters()
    }

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private fun collectCharacterFilters() {
        collectFlow(flow = characterFilterRepository.getCharacterFilters()) { characterFilters ->
            characterFilters.forEach { filter ->
                updateFilterState(
                    filterType = filter.filterType,
                    values = filter.values
                )
            }
        }
    }

    private fun updateFilterState(filterType: CharacterFilterType, values: List<String>) {
        characterFiltersMutableStateFlow.update { state ->
            when (filterType) {
                CharacterFilterType.HOUSE -> {
                    houseFilterValues = values
                    state.copy(houseFiltersSelected = values)
                }
                CharacterFilterType.GENDER -> {
                    genderFilterValues = values
                    state.copy(genderFiltersSelected = values)
                }
                CharacterFilterType.SPECIES -> state // TODO: Add speciesFiltersSelected to state when implemented
                CharacterFilterType.HOGWARTS_AFFILIATION -> state // TODO: Add hogwartsAffiliationFiltersSelected to state when implemented
                CharacterFilterType.WIZARD_STATUS -> state // TODO: Add wizardStatusFiltersSelected to state when implemented
                CharacterFilterType.ALIVE_STATUS -> state // TODO: Add aliveStatusFiltersSelected to state when implemented
            }
        }
    }

    fun onFilterHouseClicked(type: CharacterFilterType, value: String) {
        launch {
            if (type == CharacterFilterType.HOUSE) {
                val updatedValues = if (houseFilterValues.contains(value)) {
                    houseFilterValues.filter { it != value }
                } else {
                    houseFilterValues + value
                }

                // Update state
                characterFiltersMutableStateFlow.update { state ->
                    houseFilterValues = updatedValues
                    state.copy(houseFiltersSelected = updatedValues)
                }

                // Update database
                val existingFilters = characterFilterRepository.getCharacterFiltersByTypeSync(
                    filterType = CharacterFilterType.HOUSE
                )
                val existingFilter = existingFilters.firstOrNull()

                val updatedFilter = existingFilter?.copy(values = updatedValues)
                updatedFilter?.let { characterFilterRepository.updateFilter(it) }
            }
        }
    }

    fun onFilterGenderClicked(type: CharacterFilterType, value: String) {
        launch {
            if (type == CharacterFilterType.GENDER) {
                val updatedValues = if (genderFilterValues.contains(value)) {
                    genderFilterValues.filter { it != value }
                } else {
                    genderFilterValues + value
                }

                // Update state
                characterFiltersMutableStateFlow.update { state ->
                    genderFilterValues = updatedValues
                    state.copy(genderFiltersSelected = updatedValues)
                }

                // Update database
                val existingFilters = characterFilterRepository.getCharacterFiltersByTypeSync(
                    filterType = CharacterFilterType.GENDER
                )
                val existingFilter = existingFilters.firstOrNull()

                if (existingFilter != null) {
                    val updatedFilter = existingFilter.copy(values = updatedValues)
                    characterFilterRepository.updateFilter(updatedFilter)
                } else {
                    val newFilter = CharacterFilterConverter(
                        id = 0,
                        filterType = CharacterFilterType.GENDER,
                        values = updatedValues,
                        isActive = true
                    )
                    characterFilterRepository.insertFilter(newFilter)
                }
            }
        }
    }

    fun buildHouses(): List<String> =
        listOf(
            Constants.GRYFFINDOR_HOUSE,
            Constants.RAVENCLAW_HOUSE,
            Constants.SLYTHERIN_HOUSE,
            Constants.HUFFLEPUFF_HOUSE,
            Constants.NO_HOUSE_FILTER
        )

    fun buildGenders(): List<String> =
        listOf(
            Constants.MALE,
            Constants.FEMALE
        )

    fun onBackClicked() = navigator.pop(routeAction = Constants.NavigationDestinations.CHARACTERS_SCREEN)
}