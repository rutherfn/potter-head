package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing the configuration for an app bar.
 *
 * @param titleId The resource ID of the title to be displayed in the app bar.
 * @param onIconButtonClicked The action to perform when the icon button is clicked.
 * @param iconContentDescription The content description of the icon button.
 * @param imageVector The image vector to be displayed in the icon button.
 *
 * @author Nicholas Rutherford
 */
data class AppBar(
    val titleId: Int,
    val onIconButtonClicked: (() -> Unit)? = null,
    val iconContentDescription: String = "",
    val imageVector: ImageVector? = null
)