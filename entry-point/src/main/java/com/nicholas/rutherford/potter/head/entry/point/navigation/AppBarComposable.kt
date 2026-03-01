package com.nicholas.rutherford.potter.head.entry.point.navigation

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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterOrange
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterWhite
import com.nicholas.rutherford.potter.head.entry.point.navigation.appbar.AppBar

/**
 * Composable that displays an AppBar with title and optional icon button.
 *
 * This composable renders a top app bar that provides a consistent header across screens
 * in the application. The AppBar features:
 * - Orange background color ([PotterOrange]) matching the app's brand theme
 * - White text and icons for optimal contrast
 * - Edge-to-edge support with proper status bar insets handling
 * - Optional icon button (typically for navigation actions like back button)
 * - Centered title text with balanced spacing
 *
 * The AppBar automatically handles window insets for edge-to-edge displays, ensuring
 * the content is properly positioned below the system status bar. The bar has a fixed
 * height of 56dp for the content area, with additional padding for the status bar.
 *
 * The layout structure consists of:
 * - Left side: Optional icon button (48dp) or spacer for alignment
 * - Center: Title text (flexible width)
 * - Right side: Spacer (48dp) for visual balance
 *
 * This composable is typically used within [NavigationEffects] and is controlled
 * through [AppNavigationGraph.updateAppBar] to show or hide the AppBar based on
 * the current screen.
 *
 * @param appBar The [AppBar] data class containing the title, optional icon,
 *               click handler, and content description. The AppBar can be created
 *               using [AppBarFactory] implementations.
 * @param modifier Optional [Modifier] to be applied to the AppBar container.
 *                This can be used for additional styling or layout adjustments.
 *
 * @see AppBar for the data class structure
 * @see AppBarFactory for creating AppBar instances
 * @see AppNavigationGraph.updateAppBar for controlling AppBar visibility
 * @see NavigationEffects for where this composable is rendered
 *
 * @author Nicholas Rutherford
 */
@Composable
fun AppBarComposable(
    appBar: AppBar,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                // Use explicit brand orange color to match app icon
                PotterOrange
            )
            .windowInsetsPadding(
                WindowInsets.statusBars.only(WindowInsetsSides.Top)
            )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon button (back button or other action)
            if (appBar.imageVector != null && appBar.onIconButtonClicked != null) {
                IconButton(
                    onClick = { appBar.onIconButtonClicked?.invoke() },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = appBar.imageVector,
                        contentDescription = appBar.iconContentDescription,
                        tint = PotterWhite
                    )
                }
            } else {
                // Spacer to maintain alignment when no icon
                Spacer(modifier = Modifier.width(48.dp))
            }

            // Title
            Text(
                text = stringResource(appBar.titleId),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = PotterWhite,
                modifier = Modifier.weight(1f)
            )

            // Right side spacer for symmetry
            Spacer(modifier = Modifier.width(48.dp))
        }
    }
}

