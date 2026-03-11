package com.nicholas.rutherford.potter.head.feature.quizzes

data class QuizzesParams(
    val state: QuizzesState,
    val onQuizClicked: () -> Unit,
    val onFilterItemClicked: (index: Int) -> Unit = {}
)