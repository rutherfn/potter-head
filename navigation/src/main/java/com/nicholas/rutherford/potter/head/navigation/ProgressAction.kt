package com.nicholas.rutherford.potter.head.navigation

/**
 * Data class representing a progress action for displaying loading or progress indicators.
 *
 * @author Nicholas Rutherford
 */
data class ProgressAction(
    val onDismissClicked: (() -> Unit)? = null,
    val shouldBeAbleToBeDismissed: Boolean = false
)