package com.nicholas.rutherford.potter.head.navigation

/**
 * Data class representing an alert dialog with optional confirmation and dismissal buttons.
 *
 * @property title The title text displayed at the top of the alert.
 * @property onDismissClicked Optional callback invoked when the alert is dismissed.
 * @property confirmButton Optional [AlertConfirmAndDismissButton] representing the confirm action.
 * @property dismissButton Optional [AlertConfirmAndDismissButton] representing the dismiss action.
 * @property description Optional descriptive text providing more information within the alert.
 *
 * @author Nicholas Rutherford
 */
data class AlertAction(
    val title: String,
    val onDismissClicked: (() -> Unit)? = null,
    val confirmButton: AlertConfirmAndDismissButton? = null,
    val dismissButton: AlertConfirmAndDismissButton? = null,
    val description: String? = null
)
