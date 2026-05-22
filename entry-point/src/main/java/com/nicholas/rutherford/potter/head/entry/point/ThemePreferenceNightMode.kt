package com.nicholas.rutherford.potter.head.entry.point

import androidx.appcompat.app.AppCompatDelegate
import com.nicholas.rutherford.potter.head.core.theme.ThemePreference
import com.nicholas.rutherford.potter.head.core.theme.buildThemePreferenceFromValue

/**
 * Applies the user theme preference to the process-wide AppCompat night mode so the window
 * matches Compose before the first frame (reduces light/dark flash on cold start).
 */
fun applyThemePreferenceNightMode(themePreferenceValue: Int) {
    val nightMode = when (buildThemePreferenceFromValue(value = themePreferenceValue)) {
        ThemePreference.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
        ThemePreference.DARK -> AppCompatDelegate.MODE_NIGHT_YES
        ThemePreference.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    }
    AppCompatDelegate.setDefaultNightMode(nightMode)
}
