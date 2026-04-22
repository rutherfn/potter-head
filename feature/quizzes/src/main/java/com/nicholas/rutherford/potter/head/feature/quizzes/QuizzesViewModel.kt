package com.nicholas.rutherford.potter.head.feature.quizzes

import android.app.Application
import android.net.Uri
import androidx.lifecycle.LifecycleOwner
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.repository.QuizRepository
import com.nicholas.rutherford.potter.head.database.repository.SavedQuizRepository
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.toQuizzesConverter
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
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
 * @param savedQuizRepository The repository for managing saved quizzes in the local database.
 * @param navigator The navigator for navigating between screens.
 *
 * @author Nicholas Rutherford
 */
class QuizzesViewModel(
    private val application: Application,
    private val quizRepository: QuizRepository,
    private val savedQuizRepository: SavedQuizRepository,
    private val navigator: Navigator
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

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        launch {
            checkForQuizzesInDb()
            loadQuizzes()
        }
    }

    private suspend fun checkForQuizzesInDb() {
        val hasQuizzesInDb = quizRepository.getQuizCount() > 0

        if (!hasQuizzesInDb) {
            quizzesMutableStateFlow.update { state -> state.copy(isLoading = true) }
            quizRepository.insertAllQuizzesFromJson()
        }
    }

    private suspend fun loadQuizzes() {
        val quizzesFromDb = quizRepository.getAllQuizzes()
        val quizzesConverters = quizzesFromDb.map { quiz -> quiz.toQuizzesConverter() }

        val savedQuizzesFromDb = savedQuizRepository.getAllSavedQuizzes()
        val savedQuizConverters = savedQuizzesFromDb.map { quiz -> quiz.toQuizzesConverter() }

        allQuizzes = quizzesConverters
        savedQuizzes = savedQuizConverters

        if (quizzesMutableStateFlow.value.selectedFilterIndex == 0) {
            if (quizzesConverters.isNotEmpty()) {
                quizzesMutableStateFlow.update { state ->
                    state.copy(
                        quizzes = quizzesConverters,
                        isLoading = false,
                        errorType = DataErrorType.None,
                        shouldShowFilterChips = savedQuizzesFromDb.isNotEmpty()
                    )
                }
            } else {
                quizzesMutableStateFlow.update { state ->
                    state.copy(
                        quizzes = emptyList(),
                        isLoading = false,
                        shouldShowFilterChips = savedQuizzesFromDb.isNotEmpty()
                    )
                }
            }
        }
    }

    internal fun buildQuizzesToShow(index: Int): List<QuizzesConverter> {
        return if (index == 0) {
            allQuizzes
        } else {
            savedQuizzes
        }
    }

    fun onFilterItemClicked(index: Int) {
        quizzesMutableStateFlow.update { state ->
            state.copy(
                selectedFilterIndex = index,
                quizzes = buildQuizzesToShow(index = index)
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
            checkForQuizzesInDb()
            loadQuizzes()
        }
    }

    fun onQuizClicked(title: String, description: String, imageUrl: String) {
        val encodedTitle = Uri.encode(title)
        val encodedDescription = Uri.encode(description)
        val encodedImageUrl = Uri.encode(imageUrl)
        val route = Constants.NavigationDestinations.QUIZ_DETAIL_SCREEN_WITH_PARAMS
            .replace("{quiz_name}", encodedTitle)
            .replace("{quiz_description}", encodedDescription)
            .replace("{QUIZ_IMAGE_URL}", encodedImageUrl)
        navigator.navigate(
            SimpleNavigationAction(
                destination = route
            )
        )
    }
}
