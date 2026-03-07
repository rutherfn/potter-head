package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

/**
 * Parameters data class for the Character Filters Screen.
 *
 * @param state The current state of the Character Filters Screen.
 * @param houses The list of available houses.
 * @param genders The list of available genders.
 * @param species The list of available species.
 * @param hogwartsAffiliations The list of available Hogwarts affiliations.
 * @param wizardStatuses The list of available wizard statuses.
 * @param aliveStatuses The list of available alive statuses.
 * @param onFilterHouseClicked Callback for when a house filter is clicked.
 * @param onFilterGenderClicked Callback for when a gender filter is clicked.
 * @param onFilterSpeciesClicked Callback for when a species filter is clicked.
 * @param onFilterHogwartsAffiliationClicked Callback for when a Hogwarts affiliation filter is clicked.
 * @param onFilterWizardStatusClicked Callback for when a wizard status filter is clicked.
 * @param onFilterAliveStatusClicked Callback for when an alive status filter is clicked.
 *
 * @author Nicholas Rutherford
 */
data class CharacterFiltersParams(
    val state: CharacterFiltersState,
    val houses: List<String>,
    val genders: List<String>,
    val species: List<String>,
    val hogwartsAffiliations: List<String>,
    val wizardStatuses: List<String>,
    val aliveStatuses: List<String>,
    val onFilterHouseClicked: (value: String) -> Unit,
    val onFilterGenderClicked: (value: String) -> Unit,
    val onFilterSpeciesClicked: (value: String) -> Unit,
    val onFilterHogwartsAffiliationClicked: (value: String) -> Unit,
    val onFilterWizardStatusClicked: (value: String) -> Unit,
    val onFilterAliveStatusClicked: (value: String) -> Unit
)