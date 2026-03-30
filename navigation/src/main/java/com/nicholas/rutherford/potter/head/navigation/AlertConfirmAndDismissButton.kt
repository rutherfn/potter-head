package com.nicholas.rutherford.potter.head.navigation

/**
 * Data class representing a button used in an alert, which can either confirm an action or dismiss the alert.
 *
 * @property buttonText The text displayed on the button.
 * @property onButtonClicked Optional callback invoked when the button is clicked.
 *
 * @author Nicholas Rutherford
 */
data class AlertConfirmAndDismissButton(
    val buttonText: String,
        val onButtonClicked: (() -> Unit)? = null
)