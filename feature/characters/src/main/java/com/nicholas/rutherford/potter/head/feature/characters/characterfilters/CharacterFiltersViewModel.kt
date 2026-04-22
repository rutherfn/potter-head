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

/**
 * ViewModel for managing the characters filters screen state and business logic.
 * Manages all defined filters allowing the user to turn on and off filters
 *
 * @param characterFilterRepository The repository for managing character filters.
 * @param navigator The navigator for navigating between screens.
 *
 * @author Nicholas Rutherford
 */
class CharacterFiltersViewModel(
    private val characterFilterRepository: CharacterFilterRepository,
    private val navigator: Navigator
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.CHARACTERS_FILTERS

    private val characterFiltersMutableStateFlow = MutableStateFlow(CharacterFiltersState())
    val characterFiltersStateFlow: StateFlow<CharacterFiltersState> = characterFiltersMutableStateFlow.asStateFlow()

    internal var houseFilterValues: List<String> = emptyList()
    internal var genderFilterValues: List<String> = emptyList()
    internal var speciesFilterValues: List<String> = emptyList()
    internal var hogwartsAffiliationsFilterValues: List<String> = emptyList()
    internal var wizardStatusFilterValues: List<String> = emptyList()
    internal var aliveStatusFilterValues: List<String> = emptyList()


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
                CharacterFilterType.SPECIES -> {
                    speciesFilterValues = values
                    state.copy(speciesFiltersSelected = values)
                }
                CharacterFilterType.HOGWARTS_AFFILIATION -> {
                    hogwartsAffiliationsFilterValues = values
                    state.copy(hogwartsAffiliationsSelected = values)
                }
                CharacterFilterType.WIZARD_STATUS -> {
                    wizardStatusFilterValues = values
                    state.copy(wizardStatusFiltersSelected = values)
                }
                CharacterFilterType.ALIVE_STATUS -> {
                    aliveStatusFilterValues = values
                    state.copy(aliveStatusFiltersSelected = values)
                }
            }
        }
    }

    private suspend fun updateFilterValue(
        filterType: CharacterFilterType,
        currentValues: List<String>,
        value: String,
        updateState: (List<String>) -> Unit
    ) {
        val updatedValues = if (currentValues.contains(value)) {
            currentValues.filter { it != value }
        } else {
            currentValues + value
        }

        updateState(updatedValues)

        val existingFilters = characterFilterRepository.getCharacterFiltersByTypeSync(filterType = filterType)
        val existingFilter = existingFilters.firstOrNull()

        if (existingFilter != null) {
            val updatedFilter = existingFilter.copy(values = updatedValues)
            characterFilterRepository.updateFilter(updatedFilter)
        } else {
            val newFilter = CharacterFilterConverter(
                id = 0,
                filterType = filterType,
                values = updatedValues,
                isActive = true
            )
            characterFilterRepository.insertFilter(newFilter)
        }
    }

    fun onFilterHouseClicked(value: String) {
        launch {
            updateFilterValue(
                filterType = CharacterFilterType.HOUSE,
                currentValues = houseFilterValues,
                value = value
            ) { updatedValues ->
                characterFiltersMutableStateFlow.update { state ->
                    houseFilterValues = updatedValues
                    state.copy(houseFiltersSelected = updatedValues)
                }
            }
        }
    }

    fun onFilterGenderClicked(value: String) {
        launch {
            updateFilterValue(
                filterType = CharacterFilterType.GENDER,
                currentValues = genderFilterValues,
                value = value
            ) { updatedValues ->
                characterFiltersMutableStateFlow.update { state ->
                    genderFilterValues = updatedValues
                    state.copy(genderFiltersSelected = updatedValues)
                }
            }
        }
    }

    fun onFilterHogwartsAffiliationClicked(value: String) {
        launch {
            updateFilterValue(
                filterType = CharacterFilterType.HOGWARTS_AFFILIATION,
                currentValues = hogwartsAffiliationsFilterValues,
                value = value
            ) { updatedValues ->
                characterFiltersMutableStateFlow.update { state ->
                    hogwartsAffiliationsFilterValues = updatedValues
                    state.copy(hogwartsAffiliationsSelected = updatedValues)
                }
            }
        }
    }

    fun onFilterSpeciesClicked(value: String) {
        launch {
            updateFilterValue(
                filterType = CharacterFilterType.SPECIES,
                currentValues = speciesFilterValues,
                value = value
            ) { updatedValues ->
                characterFiltersMutableStateFlow.update { state ->
                    speciesFilterValues = updatedValues
                    state.copy(speciesFiltersSelected = updatedValues)
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

    fun buildHogwartsAffiliations(): List<String> =
        listOf(
            Constants.HAS_HOUSE_AFFILIATION_FILTER,
            Constants.HAS_NOT_HOUSE_AFFILIATION_FILTER
        )

    fun buildSpecies(): List<String> =
        listOf(
            Constants.SPECIES_ACROMANTULA,
            Constants.SPECIES_CAT,
            Constants.SPECIES_CENTAUR,
            Constants.SPECIES_CEPHALOPOD,
            Constants.SPECIES_DOG,
            Constants.SPECIES_DRAGON,
            Constants.SPECIES_GHOST,
            Constants.SPECIES_GIANT,
            Constants.SPECIES_GOBLIN,
            Constants.SPECIES_HALF_GIANT,
            Constants.SPECIES_HALF_HUMAN,
            Constants.SPECIES_HAT,
            Constants.SPECIES_HIPPOGRIFF,
            Constants.SPECIES_HOUSE_ELF,
            Constants.SPECIES_HUMAN,
            Constants.SPECIES_OWL,
            Constants.SPECIES_PHOENIX,
            Constants.SPECIES_POLTERGEIST,
            Constants.SPECIES_PYGMY_PUFF,
            Constants.SPECIES_SELKIE,
            Constants.SPECIES_SERPENT,
            Constants.SPECIES_SNAKE,
            Constants.SPECIES_THREE_HEADED_DOG,
            Constants.SPECIES_TOAD,
            Constants.SPECIES_VAMPIRE,
            Constants.SPECIES_WEREWOLF
        )

    fun onFilterWizardStatusClicked(value: String) {
        launch {
            updateFilterValue(
                filterType = CharacterFilterType.WIZARD_STATUS,
                currentValues = wizardStatusFilterValues,
                value = value
            ) { updatedValues ->
                characterFiltersMutableStateFlow.update { state ->
                    wizardStatusFilterValues = updatedValues
                    state.copy(wizardStatusFiltersSelected = updatedValues)
                }
            }
        }
    }

    fun buildWizardStatuses(): List<String> =
        listOf(
            Constants.IS_WIZARD_FILTER,
            Constants.IS_NOT_WIZARD_FILTER
        )

    fun onFilterAliveStatusClicked(value: String) {
        launch {
            updateFilterValue(
                filterType = CharacterFilterType.ALIVE_STATUS,
                currentValues = aliveStatusFilterValues,
                value = value
            ) { updatedValues ->
                characterFiltersMutableStateFlow.update { state ->
                    aliveStatusFilterValues = updatedValues
                    state.copy(aliveStatusFiltersSelected = updatedValues)
                }
            }
        }
    }

    fun buildAliveStatuses(): List<String> =
        listOf(
            Constants.IS_ALIVE_FILTER,
            Constants.IS_NOT_ALIVE_FILTER
        )

    fun onBackClicked() = navigator.pop(routeAction = Constants.NavigationDestinations.CHARACTERS_SCREEN)
}
