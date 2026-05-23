package com.nicholas.rutherford.potter.head.feature.settings.debug

import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.repository.QuizRepository
import com.nicholas.rutherford.potter.head.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizResultUrlsViewModel(
    private val quizRepository: QuizRepository,
    private val navigator: Navigator
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.QUIZ_RESULT_URLS

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private val quizResultUrlsMutableStateFlow = MutableStateFlow(value = QuizResultUrlsState())
    val quizResultUrlsStateFlow: StateFlow<QuizResultUrlsState> = quizResultUrlsMutableStateFlow.asStateFlow()

    init {
        launch { setInitialState() }
    }

    suspend fun setInitialState() {
        val quizzes = quizRepository.getAllQuizzes()

        if (quizzes.isEmpty()) {
            quizRepository.insertAllQuizzesFromJson()
        }

        quizResultUrlsMutableStateFlow.update { state ->
            state.copy(
                sections = QuizResultUrlSection.fromQuizzes(quizzes = quizRepository.getAllQuizzes()),
            )
        }
    }

    fun onBackClicked() = navigator.pop()

    fun onViewUrlClicked(url: String) = navigator.url(url = url)
}