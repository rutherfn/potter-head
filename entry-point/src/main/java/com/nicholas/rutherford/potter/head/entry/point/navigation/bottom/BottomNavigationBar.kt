package com.nicholas.rutherford.potter.head.entry.point.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterOrange
import com.nicholas.rutherford.potter.head.entry.point.navigation.Screens

/**
 * List of all bottom navigation bar items in the application.
 *
 * Defines the screens that should appear in the bottom navigation bar,
 * along with their corresponding icons.
 */
val bottomNavItems = listOf(
    BottomNavItem(
        screen = Screens.Characters,
        icon = Icons.Default.Group
    ),
    BottomNavItem(
        screen = Screens.Quizzes,
        icon = Icons.Default.Quiz
    ),
    BottomNavItem(
        screen = Screens.Settings,
        icon = Icons.Default.Settings
    )
)

/**
 * Composable function that builds the bottom navigation bar with all configured items.
 *
 * This function creates a [NavigationBar] containing [NavigationBarItem] composables
 * for each item in [bottomNavItems]. Each item:
 * - Shows as selected when the current destination's hierarchy contains its route
 * - Navigates to the screen's route when clicked
 * - Uses the screen's title for the label and content description
 * - Uses the configured icon
 *
 * Navigation behavior:
 * - Pops up to the start destination when navigating
 * - Saves and restores state when navigating between items
 * - Uses single top launch mode to prevent multiple instances
 *
 * @param navController The [NavController] used for navigation
 * @param currentDestination The current navigation destination,
 * used to determine which item should be selected by checking its hierarchy
 *
 * @author Nicholas Rutherford
 */
@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = Color(0xFFFFF8F5), // Very light orange-tinted background to complement theme
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.screen.title
                    )
                },
                label = { Text(item.screen.title) },
                colors = NavigationBarItemColors(
                    selectedIconColor = PotterOrange,
                    selectedTextColor = PotterOrange,
                    selectedIndicatorColor = PotterOrange.copy(alpha = 0.15f), // Light orange background for contrast
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            )
        }
    }
}

