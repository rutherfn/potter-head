package com.nicholas.rutherford.potter.head.compose.components.data

import com.nicholas.rutherford.potter.head.compose.components.InfoCard

/**
 * Data class representing an information item to display in an [InfoCard].
 *
 * @param labelId The string resource ID for the label text.
 * @param value The value text to display.
 *
 * @author Nicholas Rutherford
 */
data class InfoItem(
    val labelId: Int,
    val value: String
)