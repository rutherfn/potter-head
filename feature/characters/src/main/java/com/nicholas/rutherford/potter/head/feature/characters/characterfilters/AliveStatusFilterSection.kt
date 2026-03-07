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
 * Alive status filter section for the Character Filters Screen.
 *
 * This section displays the alive status filter options (Alive/Dead)
 * and allows users to select which alive statuses to filter by.
 *
 * @param aliveStatuses The list of available alive statuses.
 * @param selectedAliveStatuses The list of currently selected alive statuses.
 * @param onFilterAliveStatusClicked Callback when an alive status filter is clicked.
 *
 * @author Nicholas Rutherford
 */
fun LazyListScope.aliveStatusFilterSection(
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

