package com.nicholas.rutherford.potter.head.feature.settings

import com.nicholas.rutherford.potter.head.core.theme.ThemePreference

data class SettingsParams(
    val state: SettingsState,
    val onThemePreferenceSelected: (ThemePreference) -> Unit,
    val onShuffleAnswerOrderCheckedChanged: (Boolean) -> Unit,
    val onClearSavedQuizzesClick: () -> Unit,
    val onResetCharacterFiltersClick: () -> Unit,
    val onViewDataSourceClicked: () -> Unit
)
