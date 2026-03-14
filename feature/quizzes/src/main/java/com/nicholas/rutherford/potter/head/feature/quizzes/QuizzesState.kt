package com.nicholas.rutherford.potter.head.feature.quizzes

import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter

data class QuizzesState(
    val quizzes: List<QuizzesConverter> = emptyList(),
    val selectedFilterIndex: Int = 0,
    val filterTypes: List<String> = emptyList(),
    val errorType: DataErrorType = DataErrorType.None,
    val isLoading: Boolean = true
)