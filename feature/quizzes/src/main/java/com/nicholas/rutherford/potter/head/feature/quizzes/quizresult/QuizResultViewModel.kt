package com.nicholas.rutherford.potter.head.feature.quizzes.quizresult

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.database.repository.SavedQuizRepository
import com.nicholas.rutherford.potter.head.navigation.Navigator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


/**
 * ViewModel for managing the quiz result screen state and business logic..
 *
 * @param savedStateHandle The saved state handle for retrieving navigation arguments.
 * @param savedQuizRepository The repository for managing saved quizzes.
 * @param navigator The navigator for navigating between screens.
 *
 * @author Nicholas Rutherford
 */
class QuizResultViewModel(
    savedStateHandle: SavedStateHandle,
    private val savedQuizRepository: SavedQuizRepository,
    private val navigator: Navigator
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.QUIZ_RESULT

    private val quizResultMutableStateFlow = MutableStateFlow(value = QuizResultState())
    val quizResultStateFlow  = quizResultMutableStateFlow.asStateFlow()

    internal val quizIdParam: String = savedStateHandle.get<String>(Constants.NamedArguments.QUIZ_ID)?.let { value -> Uri.decode(value) } ?: ""

    init {
        launch {
            val id = quizIdParam.toLongOrNull() ?: return@launch
            val saved = savedQuizRepository.getSavedQuizById(id = id) ?: return@launch
            val quizImageUrl = saved.resultImageUrl.ifEmpty {
                saved.quizImageUrl
            }
            quizResultMutableStateFlow.value = QuizResultState(
                quizTitle = saved.quizTitle,
                quizImageUrl = quizImageUrl,
                resultText = saved.resultText,
                questions = saved.questions
            )
        }
    }

    fun onViewResultsClicked() = quizResultMutableStateFlow.update { state -> state.copy(showDetailedResults = true) }

    fun onHideResultsClicked() = quizResultMutableStateFlow.update { state -> state.copy(showDetailedResults = false) }

    fun onContinueClicked() = navigator.pop(routeAction = Constants.NavigationDestinations.QUIZZES_SCREEN)

    fun onBackClicked() = onContinueClicked()
}
