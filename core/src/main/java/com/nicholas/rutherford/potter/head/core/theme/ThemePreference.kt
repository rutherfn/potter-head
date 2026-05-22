package com.nicholas.rutherford.potter.head.core.theme

import com.nicholas.rutherford.potter.head.core.theme.ThemePreference.DARK
import com.nicholas.rutherford.potter.head.core.theme.ThemePreference.LIGHT
import com.nicholas.rutherford.potter.head.core.theme.ThemePreference.SYSTEM

/**
 * User-selected appearance for the app.
 *
 * @author Nicholas Rutherford.
 */
enum class ThemePreference(val value: Int) {
    LIGHT(value = 0),
    DARK(value = 1),
    SYSTEM(value = 2);

    fun isDarkTheme(systemInDarkTheme: Boolean): Boolean =
        when (this) {
            LIGHT -> false
            DARK -> true
            SYSTEM -> systemInDarkTheme
        }
}

fun buildThemePreferenceFromValue(value: Int): ThemePreference {
    return when (value) {
        LIGHT.value -> LIGHT
        DARK.value -> DARK
        else -> SYSTEM
    }
}
