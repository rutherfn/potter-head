package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nicholas.rutherford.potter.head.compose.ui.theme.getHouseColor

/**
 * Reusable composable for displaying a house badge with house-specific styling.
 * 
 * @param house The house name to display.
 * @param modifier Optional modifier for the badge.
 * 
 * @author Nicholas Rutherford
 */
@Composable
fun HouseBadge(
    house: String,
    modifier: Modifier = Modifier
) {
    val houseColor = getHouseColor(house = house)
    
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = houseColor.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = house.replaceFirstChar { it.uppercaseChar() },
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = houseColor,
            fontSize = 12.sp
        )
    }
}


