package com.nicholas.rutherford.potter.head.entry.point.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination

/**
 * Data class representing a bottom navigation bar item configuration.
 *
 * @property screen The [Screens] object representing the navigation destination
 * @property icon The [ImageVector] icon to display for this item
 *
 * @author Nicholas Rutherford
 */
data class BottomNavItem(
    val screen: Screens,
    val icon: ImageVector
)

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
    NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
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
                label = { Text(item.screen.title) }
            )
        }
    }
}

