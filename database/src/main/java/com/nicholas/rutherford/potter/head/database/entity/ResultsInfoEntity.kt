package com.nicholas.rutherford.potter.head.database.entity

/**
 * Rich metadata for one possible quiz outcome (answer text, blurb, image).
 * Stored as JSON inside [QuizEntity.resultsInfo] via [com.nicholas.rutherford.potter.head.database.typeconverter.DatabaseTypeConverters], not as its own table.
 *
 * @property answer The final result or "answer" to the completed quiz
 * @property moreInfo Additional information about the answer
 * @property imageUrl The URL of the image answer result
 *
 * @author Nicholas Rutherford
 */
data class ResultsInfoEntity(
    val answer: String,
    val moreInfo: String,
    val imageUrl: String
)
