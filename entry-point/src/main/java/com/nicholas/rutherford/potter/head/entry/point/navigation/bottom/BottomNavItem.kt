package com.nicholas.rutherford.potter.head.entry.point.navigation.bottom

import androidx.compose.ui.graphics.vector.ImageVector
import com.nicholas.rutherford.potter.head.entry.point.navigation.Screens

/**
 * Data class representing a bottom navigation bar item configuration.
 *
 * @property screen The [Screens] object representing the navigation destination
 * @property icon The [ImageVector] icon to display for this item
 *
 * @author Nicholas Rutherford
 */
data class BottomNavItem(val screen: Screens, val icon: ImageVector)