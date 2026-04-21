package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme

/**
 * Reusable composable for selection state used to show enabled vs disabled state
 *
 * @param isSelected The current selection state.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun SelectionIndicator(isSelected: Boolean) {
    val color by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outline
        },
        label = "indicatorColor"
    )

    if (isSelected) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(color),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.onPrimary)
            )
        }
    } else {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .border(2.dp, color, CircleShape)
        )
    }
}

@Preview(showBackground = true, name = "SelectionIndicator — selected")
@Composable
private fun SelectionIndicatorPreviewSelected() {
    PotterHeadTheme(darkTheme = false) {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            SelectionIndicator(isSelected = true)
        }
    }
}

@Preview(showBackground = true, name = "SelectionIndicator — not selected")
@Composable
private fun SelectionIndicatorPreviewNotSelected() {
    PotterHeadTheme(darkTheme = false) {
        Box(
            modifier = Modifier.padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            SelectionIndicator(isSelected = false)
        }
    }
}
