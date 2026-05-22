package com.nicholas.rutherford.potter.head.feature.settings

import com.nicholas.rutherford.potter.head.core.theme.ThemePreference

data class SettingsState(
    val versionName: String = "",
    val themeOptions: List<String> = emptyList(),
    val selectedTheme: ThemePreference = ThemePreference.SYSTEM,
    val shouldShuffleAnswerOrderChecked: Boolean = false
)
