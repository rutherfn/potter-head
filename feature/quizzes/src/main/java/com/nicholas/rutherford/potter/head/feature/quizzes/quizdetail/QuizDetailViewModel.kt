package com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.base.view.model.FlowCollectionTrigger
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel for managing the quiz detail screen state and business logic..
 *
 * @param savedStateHandle The saved state handle for retrieving navigation arguments.
 * @param navigator The navigator for navigating between screens.
 *
 * @author Nicholas Rutherford
 */
class QuizDetailViewModel(
    savedStateHandle: SavedStateHandle,
    private val navigator: Navigator
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.QUIZ_DETAIL

    private val quizDetailMutableStateFlow = MutableStateFlow(value = QuizDetailState())
    val quizDetailStateFlow: StateFlow<QuizDetailState> = quizDetailMutableStateFlow.asStateFlow()

    internal val quizNameParam: String = savedStateHandle.get<String>(Constants.NamedArguments.QUIZ_NAME)?.let { value -> Uri.decode(value) } ?: ""
    internal val quizDescriptionParam: String = savedStateHandle.get<String>(Constants.NamedArguments.QUIZ_DESCRIPTION)?.let { value -> Uri.decode(value) } ?: ""
    internal val quizImageUrlParam: String = savedStateHandle.get<String>(Constants.NamedArguments.QUIZ_IMAGE_URL)?.let { value -> Uri.decode(value) } ?: ""

    init {
        updateStateFromParams()
    }

    override fun getFlowCollectionTrigger(): FlowCollectionTrigger = FlowCollectionTrigger.INIT

    private fun updateStateFromParams() {
        quizDetailMutableStateFlow.update { state ->
            state.copy(
                title = quizNameParam,
                description = quizDescriptionParam,
                imageUrl = quizImageUrlParam
            )
        }
    }

    fun onBackClicked() = navigator.pop(routeAction = Constants.NavigationDestinations.QUIZZES_SCREEN)

    fun onStartQuizClicked(title: String) {
        val encodedTitle = Uri.encode(title)
        val route = Constants.NavigationDestinations.TAKE_QUIZ_SCREEN_WITH_PARAMS.replace("{quiz_name}", encodedTitle)
        navigator.navigate(navigationAction = SimpleNavigationAction(destination = route))
    }
}