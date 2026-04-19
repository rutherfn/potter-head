package com.nicholas.rutherford.potter.head.navigation

/**
 * Data class representing a progress action for displaying loading or progress indicators.
 *
 * @param onDismissClicked Optional callback to be invoked when the progress action is dismissed.
 * @param shouldBeAbleToBeDismissed Whether the progress action should be dismissed
 *
 * @author Nicholas Rutherford
 */
data class ProgressAction(
    val onDismissClicked: (() -> Unit)? = null,
    val shouldBeAbleToBeDismissed: Boolean = false
)
