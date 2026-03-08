package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

/**
 * Data class representing an information item to display in an [InfoCard].
 *
 * @param labelId The string resource ID for the label text.
 * @param value The value text to display.
 *
 * @author Nicholas Rutherford
 */
data class InfoItem(
    val labelId: Int,
    val value: String
)

/**
 * Reusable composable for displaying a card containing a title and a list of information items.
 *
 * This component displays a [Card] with a title and a list of [InfoItem] entries.
 * Each item displays a label (from string resources) and a value in a row format.
 *
 * @param title The title text to display at the top of the card.
 * @param items The list of [InfoItem] to display in the card.
 * @param modifier Optional modifier for the card.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun InfoCard(
    title: String,
    items: List<InfoItem>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            items.forEach { item ->
                InfoRow(
                    labelId = item.labelId,
                    value = item.value
                )
            }
        }
    }
}

/**
 * Reusable composable for displaying a single information row with a label and value.
 *
 * This component displays a label (from string resources) on the left and a value on the right,
 * with proper spacing and styling.
 *
 * @param labelId The string resource ID for the label text.
 * @param value The value text to display.
 * @param modifier Optional modifier for the row.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun InfoRow(
    labelId: Int,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(id = labelId),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}

