package com.nicholas.rutherford.potter.head.feature.quizzes

import android.app.Application
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.repository.QuizRepository
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.toQuizzesConverter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for managing the quizzes list screen state and business logic.
 * Follows a cache-first strategy: loads from local database, fetches from JSON if empty.
 *
 * @param application The application context for accessing string resources.
 * @param quizRepository The repository for managing quizzes in the local database.
 *
 * @author Nicholas Rutherford
 */
class QuizzesViewModel(
    private val application: Application,
    private val quizRepository: QuizRepository
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.QUIZZES

    private val quizzesMutableStateFlow = MutableStateFlow(QuizzesState())
    val quizzesStateFlow: StateFlow<QuizzesState> = quizzesMutableStateFlow.asStateFlow()

    private var allQuizzes: List<QuizzesConverter> = emptyList()
    private var savedQuizzes: List<QuizzesConverter> = emptyList()

    init {
        launch { checkForQuizzesForDb() }
        launch { collectAllQuizzes() }
        quizzesMutableStateFlow.update { state ->
            state.copy(
                filterTypes = listOf(
                    application.getString(StringIds.availableQuizzes),
                    application.getString(StringIds.submittedQuizzes)
                )
            )
        }
    }

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private suspend fun checkForQuizzesForDb() {
        val hasQuizzesInDb = quizRepository.getQuizCount() > 0

        if (!hasQuizzesInDb) {
            quizzesMutableStateFlow.update { state -> state.copy(isLoading = true) }
            quizRepository.insertAllQuizzesFromJson()
        }
    }

    private suspend fun collectAllQuizzes() {
        collectFlow(flow = quizRepository.getAllQuizzes()) { quizzesFromDb ->
            val quizzesConverters = quizzesFromDb.map { quiz -> quiz.toQuizzesConverter() }
            allQuizzes = quizzesConverters

            if (quizzesMutableStateFlow.value.selectedFilterIndex == 0) {
                if (quizzesConverters.isNotEmpty()) {
                    quizzesMutableStateFlow.update { state ->
                        state.copy(
                            quizzes = quizzesConverters,
                            isLoading = false,
                            errorType = DataErrorType.None
                        )
                    }
                } else {
                    quizzesMutableStateFlow.update { state ->
                        state.copy(
                            quizzes = emptyList(),
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onFilterItemClicked(index: Int) {
        quizzesMutableStateFlow.update { state ->
            val quizzesToShow = if (index == 0) {
                allQuizzes
            } else {
                savedQuizzes
            }
            state.copy(
                selectedFilterIndex = index,
                quizzes = quizzesToShow
            )
        }
    }

    fun retryLoadingQuizzes() {
        quizzesMutableStateFlow.update { state ->
            state.copy(
                errorType = DataErrorType.None,
                isLoading = true
            )
        }
        launch {
            checkForQuizzesForDb()
            collectAllQuizzes()
        }
    }

    fun onQuizClicked() {

    }
}