package com.nicholas.rutherford.potter.head.feature.settings.debug

data class QuizResultUrlsParams(
    val state: QuizResultUrlsState,
    val onViewUrlClicked: (String) -> Unit,
)