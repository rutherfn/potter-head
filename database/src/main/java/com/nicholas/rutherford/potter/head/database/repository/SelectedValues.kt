package com.nicholas.rutherford.potter.head.database.repository

/**
 * Data class representing the selected values for primary character filters.
 *
 * This class holds the currently selected filter values for the primary filters
 * that are applied in the initial filtering stage. These values are extracted
 * from the filter converters and used to filter the character list.
 *
 * The selected values represent the user's filter choices for:
 * - House filter values
 * - Gender filter values
 * - Species filter values
 * - Hogwarts affiliation filter values
 *
 * @property houseValues List of selected house filter values.
 * @property genderValues List of selected gender filter values.
 * @property speciesValues List of selected species filter values.
 * @property hogwartsAffiliationValues List of selected Hogwarts affiliation filter values.
 *
 * @author Nicholas Rutherford
 */
data class SelectedValues(
    val houseValues: List<String>,
    val genderValues: List<String>,
    val speciesValues: List<String>,
    val hogwartsAffiliationValues: List<String>
)
