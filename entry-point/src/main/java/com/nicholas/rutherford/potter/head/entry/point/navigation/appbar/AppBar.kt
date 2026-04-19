package com.nicholas.rutherford.potter.head.entry.point.navigation.appbar

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data class representing the configuration for an app bar.
 *
 * @param titleId The resource ID of the title to be displayed in the app bar.
 * @param titleFormatArgs Optional format arguments for the title string resource (e.g. for "Question %1$d of %2$d").
 * @param onIconButtonClicked The action to perform when the icon button is clicked.
 * @param iconContentDescription The content description of the icon button.
 * @param imageVector The image vector to be displayed in the icon button.
 *
 * @author Nicholas Rutherford
 */
data class AppBar(
    val titleId: Int,
    val titleFormatArgs: Array<Any>? = null,
    val onIconButtonClicked: (() -> Unit)? = null,
    val iconContentDescription: String = "",
    val imageVector: ImageVector? = null
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AppBar) return false
        if (titleId != other.titleId) return false
        if (titleFormatArgs != null) {
            if (other.titleFormatArgs == null) return false
            if (!titleFormatArgs.contentEquals(other.titleFormatArgs)) return false
        } else if (other.titleFormatArgs != null) return false
        if (onIconButtonClicked != other.onIconButtonClicked) return false
        if (iconContentDescription != other.iconContentDescription) return false
        if (imageVector != other.imageVector) return false
        return true
    }

    override fun hashCode(): Int {
        var result = titleId
        result = 31 * result + (titleFormatArgs?.contentHashCode() ?: 0)
        result = 31 * result + (onIconButtonClicked?.hashCode() ?: 0)
        result = 31 * result + iconContentDescription.hashCode()
        result = 31 * result + (imageVector?.hashCode() ?: 0)
        return result
    }
}
