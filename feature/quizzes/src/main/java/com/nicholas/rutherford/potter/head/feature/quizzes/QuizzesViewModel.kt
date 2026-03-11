package com.nicholas.rutherford.potter.head.feature.quizzes

import android.app.Application
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizzesViewModel(
    private val application: Application
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.QUIZZES

    private val quizzesMutableStateFlow = MutableStateFlow(QuizzesState())
    val quizzesStateFlow: StateFlow<QuizzesState> = quizzesMutableStateFlow.asStateFlow()

    private var allQuizzes: List<QuizzesConverter> = emptyList()
    private var savedQuizzes: List<QuizzesConverter> = emptyList()

    init {
        quizzesMutableStateFlow.update { state ->
            state.copy(
                filterTypes = listOf(
                    application.getString(StringIds.availableQuizzes),
                    application.getString(StringIds.submittedQuizzes)
                )
            )
        }
    }

    fun onFilterItemClicked(index: Int) {
        quizzesMutableStateFlow.update { state ->
            state.copy(selectedFilterIndex = index)
        }
    }

    fun onQuizClicked() {

    }
}