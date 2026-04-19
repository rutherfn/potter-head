package com.nicholas.rutherford.potter.head.entry.point.navigation.bottom

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
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
 * Bottom navigation bar component that displays the main navigation items for the application.
 * 
 * @param navController The [NavController] used for handling navigation actions.
 * @param currentDestination The current [NavDestination] used to determine which item is selected.
 * 
 * @author Nicholas Rutherford
 */
@Composable
fun BottomNavigationBar(
    navController: NavController,
    currentDestination: NavDestination?
) {
    val bottomNavItems = listOf(
        BottomNavItem(
            screen = Screens.Characters,
            icon = Icons.Default.Group
        ),
        BottomNavItem(
            screen = Screens.Spells,
            icon = Icons.Default.AutoAwesome
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

    NavigationBar(
        windowInsets = NavigationBarDefaults.windowInsets,
        containerColor = Color(0xFFFFF8F5),
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        bottomNavItems.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { navDestination -> navDestination.route == item.screen.route } == true,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = { Icon(imageVector = item.icon, contentDescription = item.screen.title) },
                label = { Text(item.screen.title) },
                colors = NavigationBarItemColors(
                    selectedIconColor = PotterOrange,
                    selectedTextColor = PotterOrange,
                    selectedIndicatorColor = PotterOrange.copy(alpha = 0.15f),
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    disabledIconColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f),
                    disabledTextColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
                )
            )
        }
    }
}
