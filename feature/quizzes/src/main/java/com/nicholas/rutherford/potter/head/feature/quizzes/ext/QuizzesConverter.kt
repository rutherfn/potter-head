package com.nicholas.rutherford.potter.head.feature.quizzes.ext

data class QuizzesConverter(
    val title: String,
    val description: String,
    val longDescription: String,
    val imageUrl: String,
    val type: QuizType,

)