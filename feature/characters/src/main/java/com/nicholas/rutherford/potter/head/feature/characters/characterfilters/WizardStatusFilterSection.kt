package com.nicholas.rutherford.potter.head.feature.characters.characterfilters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds

/**
 * Wizard status filter section for the Character Filters Screen.
 *
 * This section displays the wizard status filter options (Wizard/Non-Wizard)
 * and allows users to select which wizard statuses to filter by.
 *
 * @param wizardStatuses The list of available wizard statuses.
 * @param selectedWizardStatuses The list of currently selected wizard statuses.
 * @param onFilterWizardStatusClicked Callback when a wizard status filter is clicked.
 *
 * @author Nicholas Rutherford
 */
fun LazyListScope.wizardStatusFilterSection(
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
