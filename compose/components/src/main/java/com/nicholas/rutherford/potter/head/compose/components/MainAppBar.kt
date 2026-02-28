package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme

/**
 * Composable that displays a top app bar with title and optional icon button.
 * Adapts to light/dark theme and handles edge-to-edge displays.
 *
 * @param title The title to be displayed in the app bar.
 * @param modifier Optional modifier for the app bar.
 * @param onIconButtonClicked Optional callback to be invoked when the icon button is clicked.
 * @param iconContentDescription Optional description for the icon button
 * @param imageVector Optional image vector for the icon button
 *
 * @author Nicholas Rutherford
 */
@Composable
fun MainAppBar(
    title: String,
    modifier: Modifier = Modifier,
    onIconButtonClicked: (() -> Unit)? = null,
    iconContentDescription: String = "",
    imageVector: ImageVector? = null
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.primary)
            .windowInsetsPadding(insets = WindowInsets.statusBars.only(WindowInsetsSides.Top))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon button (back button or other action)
            if (imageVector != null && onIconButtonClicked != null) {
                IconButton(
                    onClick = { onIconButtonClicked.invoke() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = imageVector,
                        contentDescription = iconContentDescription,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                // Spacer to maintain alignment when no icon
                Spacer(modifier = Modifier.width(48.dp))
            }

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

@Preview(showBackground = true, name = "AppBar - Title Only (Light)")
@Composable
private fun MainAppBarPreviewLight() {
    PotterHeadTheme(darkTheme = false) {
        MainAppBar(
            title = "Characters"
        )
    }
}

@Preview(showBackground = true, name = "AppBar - With Icon (Light)")
@Composable
private fun MainAppBarPreviewWithIconLight() {
    PotterHeadTheme(darkTheme = false) {
        MainAppBar(
            title = "Character Detail",
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            iconContentDescription = "Back",
            onIconButtonClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "AppBar - Title Only (Dark)")
@Composable
private fun MainAppBarPreviewDark() {
    PotterHeadTheme(darkTheme = true) {
        MainAppBar(
            title = "Characters"
        )
    }
}

@Preview(showBackground = true, name = "AppBar - With Icon (Dark)")
@Composable
private fun MainAppBarPreviewWithIconDark() {
    PotterHeadTheme(darkTheme = true) {
        MainAppBar(
            title = "Character Detail",
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            iconContentDescription = "Back",
            onIconButtonClicked = {}
        )
    }
}

