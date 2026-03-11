package com.nicholas.rutherford.potter.head.feature.quizzes

import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter

data class QuizzesState(
    val quizzes: List<QuizzesConverter> = emptyList(),
    val errorType: DataErrorType = DataErrorType.None
)