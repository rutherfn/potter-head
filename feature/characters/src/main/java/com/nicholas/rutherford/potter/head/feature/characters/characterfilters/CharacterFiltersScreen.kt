package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholas.rutherford.potter.head.compose.ui.theme.getHouseColor
import com.nicholas.rutherford.potter.head.compose.ui.theme.getSpeciesColor
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds

@Composable
fun CharacterFiltersScreen(params: CharacterFiltersParams) {
    val state = params.state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        houseFilterSection(
            houses = params.houses,
            selectedHouses = state.houseFiltersSelected,
            onFilterHouseClicked = { house -> params.onFilterHouseClicked(house) }
        )

        genderFilterSection(
            genders = params.genders,
            selectedGenders = state.genderFiltersSelected,
            onFilterGenderClicked = { gender -> params.onFilterGenderClicked(gender) }
        )

        speciesFilterSection(
            species = params.species,
            selectedSpecies = state.speciesFiltersSelected,
            onFilterSpeciesClicked = { specie -> params.onFilterSpeciesClicked(specie) }
        )

        hogwartsAffiliationFilterSection(
            hogwartsAffiliations = params.hogwartsAffiliations,
            selectedHogwartsAffiliations = state.hogwartsAffiliationsSelected,
            onFilterHogwartsAffiliationClicked = { hogwartsAffiliation -> params.onFilterHogwartsAffiliationClicked(hogwartsAffiliation) }
        )

        wizardStatusFilterSection(
            wizardStatuses = params.wizardStatuses,
            selectedWizardStatuses = state.wizardStatusFiltersSelected,
            onFilterWizardStatusClicked = { wizardStatus -> params.onFilterWizardStatusClicked(wizardStatus) }
        )

        aliveStatusFilterSection(
            aliveStatuses = params.aliveStatuses,
            selectedAliveStatuses = state.aliveStatusFiltersSelected,
            onFilterAliveStatusClicked = { aliveStatus -> params.onFilterAliveStatusClicked(aliveStatus) }
        )
    }
}

private fun LazyListScope.houseFilterSection(
    houses: List<String>,
    selectedHouses: List<String>,
    onFilterHouseClicked: (String) -> Unit
) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = StringIds.filterByHouse),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = StringIds.selectOneOrMoreHousesToFilterTheCharacterListOnlyCharactersFromSelectedHousesWillBeDisplayed),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    items(items = houses) { house ->
        HouseFilterItem(
            house = house,
            isSelected = selectedHouses.contains(house),
            onClick = { onFilterHouseClicked(house) }
        )
    }
}

private fun LazyListScope.genderFilterSection(
    genders: List<String>,
    selectedGenders: List<String>,
    onFilterGenderClicked: (String) -> Unit
) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = StringIds.filterByGender),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = StringIds.selectOneOrMoreGendersToFilterTheCharacterListOnlyCharactersFromSelectedGendersWillBeDisplayed),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    items(items = genders) { gender ->
        GenderFilterItem(
            gender = gender,
            isSelected = selectedGenders.contains(gender),
            onClick = { onFilterGenderClicked(gender) }
        )
    }
}

private fun LazyListScope.hogwartsAffiliationFilterSection(
    hogwartsAffiliations: List<String>,
    selectedHogwartsAffiliations: List<String>,
    onFilterHogwartsAffiliationClicked: (String) -> Unit
) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = StringIds.filterByStudentOrStaff),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = StringIds.selectWhetherToShowCharactersWhoAreStudentsOrStaffAtHogwartsOrThoseWhoAreNot),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    items(items = hogwartsAffiliations) { hogwartsAffiliationItem ->
        HogwartsAffiliationFilterItem(
            hogwartsAffiliation = hogwartsAffiliationItem,
            isSelected = selectedHogwartsAffiliations.contains(hogwartsAffiliationItem),
            onClick = { onFilterHogwartsAffiliationClicked(hogwartsAffiliationItem) }
        )
    }
}

private fun LazyListScope.speciesFilterSection(
    species: List<String>,
    selectedSpecies: List<String>,
    onFilterSpeciesClicked: (String) -> Unit
) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = StringIds.filterBySpecies),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = StringIds.selectOneOrMoreSpeciesToFilterTheCharacterListOnlyCharactersFromSelectedSpeciesWillBeDisplayed),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    items(items = species) { speciesItem ->
        SpeciesFilterItem(
            species = speciesItem,
            isSelected = selectedSpecies.contains(speciesItem),
            onClick = { onFilterSpeciesClicked(speciesItem) }
        )
    }
}

@Composable
private fun HouseFilterItem(
    house: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HouseBadge(house = house, isSelected = isSelected)
            SelectionIndicator(isSelected = isSelected, house = house)
        }
    }
}

@Composable
private fun HouseBadge(
    house: String,
    isSelected: Boolean
) {
    val isNoHouse = house == Constants.NO_HOUSE_FILTER
    val houseColor = if (isNoHouse) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        getHouseColor(house)
    }
    val houseDisplayName = if (isNoHouse) {
        stringResource(id = StringIds.noHouse)
    } else {
        house.replaceFirstChar { value -> value.uppercaseChar() }
    }
    
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = if (isNoHouse) {
                    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = if (isSelected) {
                        0.5f
                    } else {
                        0.3f
                    })
                } else {
                    houseColor.copy(alpha = if (isSelected) {
                        0.3f
                    } else {
                        0.2f
                    })
                }
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = houseDisplayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = houseColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SelectionIndicator(
    isSelected: Boolean,
    house: String
) {
    val isNoHouse = house == Constants.NO_HOUSE_FILTER
    val indicatorColor = if (isSelected) {
        if (isNoHouse) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            getHouseColor(house = house)
        }
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val checkmarkColor = if (isSelected) {
        if (isNoHouse) {
            MaterialTheme.colorScheme.surface
        } else if (house == Constants.HUFFLEPUFF_HOUSE) {
            MaterialTheme.colorScheme.onSurface
        } else {
            Color.White
        }
    } else {
        Color.Transparent
    }
    
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(indicatorColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = Constants.CHECKMARK,
                color = checkmarkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun GenderFilterItem(
    gender: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            GenderBadge(gender = gender, isSelected = isSelected)
            GenderSelectionIndicator(isSelected = isSelected)
        }
    }
}

@Composable
private fun GenderBadge(
    gender: String,
    isSelected: Boolean
) {
    val genderColor = MaterialTheme.colorScheme.primary
    val genderDisplayName = when (gender) {
        Constants.MALE -> "Male"
        Constants.FEMALE -> "Female"
        else -> gender.replaceFirstChar { value -> value.uppercaseChar() }
    }
    
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = genderColor.copy(alpha = if (isSelected) {
                    0.3f
                } else {
                    0.2f
                })
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = genderDisplayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = genderColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun GenderSelectionIndicator(isSelected: Boolean) {
    val indicatorColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val checkmarkColor = if (isSelected) {
        Color.White
    } else {
        Color.Transparent
    }
    
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(indicatorColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = Constants.CHECKMARK,
                color = checkmarkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun SpeciesFilterItem(
    species: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SpeciesBadge(species = species, isSelected = isSelected)
            SpeciesSelectionIndicator(isSelected = isSelected, species = species)
        }
    }
}

@Composable
private fun SpeciesBadge(
    species: String,
    isSelected: Boolean
) {
    val speciesColor = getSpeciesColor(species)
    val speciesDisplayName = species.replaceFirstChar { value -> value.uppercaseChar() }
        .replace("-", " ")
        .replace("_", " ")
    
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = speciesColor.copy(alpha = if (isSelected) {
                    0.3f
                } else {
                    0.2f
                })
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = speciesDisplayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = speciesColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun SpeciesSelectionIndicator(
    isSelected: Boolean,
    species: String
) {
    val speciesColor = getSpeciesColor(species)
    val indicatorColor = if (isSelected) {
        speciesColor
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val checkmarkColor = if (isSelected) {
        // Use white for most species, but adjust for lighter colors
        when (species.lowercase()) {
            Constants.SPECIES_PHOENIX,
            Constants.SPECIES_PYGMY_PUFF -> MaterialTheme.colorScheme.onSurface
            else -> Color.White
        }
    } else {
        Color.Transparent
    }
    
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(indicatorColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = Constants.CHECKMARK,
                color = checkmarkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun HogwartsAffiliationFilterItem(
    hogwartsAffiliation: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HogwartsAffiliationBadge(hogwartsAffiliation = hogwartsAffiliation, isSelected = isSelected)
            HogwartsAffiliationSelectionIndicator(isSelected = isSelected)
        }
    }
}

@Composable
private fun HogwartsAffiliationBadge(
    hogwartsAffiliation: String,
    isSelected: Boolean
) {
    val affiliationColor = MaterialTheme.colorScheme.primary
    val affiliationDisplayName = when (hogwartsAffiliation.lowercase()) {
        Constants.HAS_HOUSE_AFFILIATION_FILTER.lowercase() -> "Student/Staff"
        Constants.HAS_NOT_HOUSE_AFFILIATION_FILTER.lowercase() -> "Other"
        else -> hogwartsAffiliation.replaceFirstChar { value -> value.uppercaseChar() }
            .replace("Hogwarts", "")
            .replace("Affiliation", "Affiliation")
            .replace("Filter", "")
            .trim()
    }
    
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = affiliationColor.copy(alpha = if (isSelected) {
                    0.3f
                } else {
                    0.2f
                })
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = affiliationDisplayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = affiliationColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun HogwartsAffiliationSelectionIndicator(isSelected: Boolean) {
    val indicatorColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val checkmarkColor = if (isSelected) {
        Color.White
    } else {
        Color.Transparent
    }
    
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(indicatorColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = Constants.CHECKMARK,
                color = checkmarkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun LazyListScope.wizardStatusFilterSection(
    wizardStatuses: List<String>,
    selectedWizardStatuses: List<String>,
    onFilterWizardStatusClicked: (String) -> Unit
) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = StringIds.filterByWizardStatus),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = StringIds.selectWhetherToShowWizardsOrNonWizards),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    items(items = wizardStatuses) { wizardStatusItem ->
        WizardStatusFilterItem(
            wizardStatus = wizardStatusItem,
            isSelected = selectedWizardStatuses.contains(wizardStatusItem),
            onClick = { onFilterWizardStatusClicked(wizardStatusItem) }
        )
    }
}

@Composable
private fun WizardStatusFilterItem(
    wizardStatus: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            WizardStatusBadge(wizardStatus = wizardStatus, isSelected = isSelected)
            WizardStatusSelectionIndicator(isSelected = isSelected)
        }
    }
}

@Composable
private fun WizardStatusBadge(
    wizardStatus: String,
    isSelected: Boolean
) {
    val wizardStatusColor = MaterialTheme.colorScheme.primary
    val wizardStatusDisplayName = when (wizardStatus.lowercase()) {
        Constants.IS_WIZARD_FILTER.lowercase() -> "Wizard"
        Constants.IS_NOT_WIZARD_FILTER.lowercase() -> "Non-Wizard"
        else -> wizardStatus.replaceFirstChar { value -> value.uppercaseChar() }
            .replace("Wizard", "")
            .replace("Filter", "")
            .trim()
    }
    
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = wizardStatusColor.copy(alpha = if (isSelected) {
                    0.3f
                } else {
                    0.2f
                })
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = wizardStatusDisplayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = wizardStatusColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun WizardStatusSelectionIndicator(isSelected: Boolean) {
    val indicatorColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val checkmarkColor = if (isSelected) {
        Color.White
    } else {
        Color.Transparent
    }
    
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(indicatorColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = Constants.CHECKMARK,
                color = checkmarkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

private fun LazyListScope.aliveStatusFilterSection(
    aliveStatuses: List<String>,
    selectedAliveStatuses: List<String>,
    onFilterAliveStatusClicked: (String) -> Unit
) {
    item {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(id = StringIds.filterByAliveStatus),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = stringResource(id = StringIds.selectWhetherToShowAliveOrDeadCharacters),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 20.sp
            )
        }
    }

    items(items = aliveStatuses) { aliveStatusItem ->
        AliveStatusFilterItem(
            aliveStatus = aliveStatusItem,
            isSelected = selectedAliveStatuses.contains(aliveStatusItem),
            onClick = { onFilterAliveStatusClicked(aliveStatusItem) }
        )
    }
}

@Composable
private fun AliveStatusFilterItem(
    aliveStatus: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AliveStatusBadge(aliveStatus = aliveStatus, isSelected = isSelected)
            AliveStatusSelectionIndicator(isSelected = isSelected)
        }
    }
}

@Composable
private fun AliveStatusBadge(
    aliveStatus: String,
    isSelected: Boolean
) {
    val aliveStatusColor = MaterialTheme.colorScheme.primary
    val aliveStatusDisplayName = when (aliveStatus.lowercase()) {
        Constants.IS_ALIVE_FILTER.lowercase() -> "Alive"
        Constants.IS_NOT_ALIVE_FILTER.lowercase() -> "Dead"
        else -> aliveStatus.replaceFirstChar { value -> value.uppercaseChar() }
            .replace("Alive", "")
            .replace("Filter", "")
            .trim()
    }
    
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(
                color = aliveStatusColor.copy(alpha = if (isSelected) {
                    0.3f
                } else {
                    0.2f
                })
            )
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = aliveStatusDisplayName,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = aliveStatusColor,
            fontSize = 12.sp
        )
    }
}

@Composable
private fun AliveStatusSelectionIndicator(isSelected: Boolean) {
    val indicatorColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    
    val checkmarkColor = if (isSelected) {
        Color.White
    } else {
        Color.Transparent
    }
    
    Box(
        modifier = Modifier
            .size(28.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(indicatorColor),
        contentAlignment = Alignment.Center
    ) {
        if (isSelected) {
            Text(
                text = Constants.CHECKMARK,
                color = checkmarkColor,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}