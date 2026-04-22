package com.nicholas.rutherford.potter.head.feature.quizzes.takequiz

import android.app.Application
import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import com.nicholas.rutherford.potter.head.base.view.model.BaseViewModel
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.converter.QuizConverter
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity
import com.nicholas.rutherford.potter.head.database.repository.QuizRepository
import com.nicholas.rutherford.potter.head.database.repository.SavedQuizRepository
import com.nicholas.rutherford.potter.head.navigation.AlertAction
import com.nicholas.rutherford.potter.head.navigation.AlertConfirmAndDismissButton
import com.nicholas.rutherford.potter.head.navigation.Navigator
import com.nicholas.rutherford.potter.head.navigation.ProgressAction
import com.nicholas.rutherford.potter.head.navigation.SimpleNavigationAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TakeQuizViewModel(
    savedStateHandle: SavedStateHandle,
    private val application: Application,
    private val navigator: Navigator,
    private val quizRepository: QuizRepository,
    private val savedQuizRepository: SavedQuizRepository
) : BaseViewModel() {

    override val screenTitle: String = Constants.ScreenTitles.TAKE_QUIZ

    private val takeQuizMutableStateFlow = MutableStateFlow(value = TakeQuizState())
    val takeQuizStateFlow: StateFlow<TakeQuizState> = takeQuizMutableStateFlow.asStateFlow()

    internal val quizNameParam: String = savedStateHandle.get<String>(Constants.NamedArguments.QUIZ_NAME)?.let { value -> Uri.decode(value) } ?: ""

    internal var currentQuiz: QuizConverter? = null
    internal var savedQuiz: QuizConverter? = null
    private val selectedAnswers: ArrayList<AnswerEntity> = arrayListOf()

    init {
        launch { updateStateFromParams() }
    }

    private suspend fun updateStateFromParams() {
        quizRepository.getQuizByTitle(title = quizNameParam)?.let { quiz ->
            currentQuiz = quiz
            takeQuizMutableStateFlow.update { state ->
                state.copy(
                    questions = quiz.questions,
                    questionSize = quiz.questions.size
                )
            }
        }
    }

    internal fun buildNewSelectedAnswerIndex(selectedAnswerIndex: Int?, answerIndex: Int): Int? {
        return if (selectedAnswerIndex == answerIndex) {
            null
        } else {
            answerIndex
        }
    }

    internal fun buildSelectingAnswerDescriptionStringId(isLastQuestion: Boolean): Int {
        return if (isLastQuestion) {
            StringIds.pleaseSelectAAnswerInOrderToFinishTheQuiz
        } else {
            StringIds.pleaseSelectAAnswerInOrderToContinueTheQuiz
        }
    }

    fun onAnswerSelected(answerIndex: Int) {
        takeQuizMutableStateFlow.update { state ->
            state.copy(selectedAnswerIndex = buildNewSelectedAnswerIndex(selectedAnswerIndex = state.selectedAnswerIndex, answerIndex = answerIndex))
        }
    }

    internal fun clearCurrentQuizAttemptState() {
        savedQuiz = null
        selectedAnswers.clear()
    }

    fun onBackClicked() {
        val currentQuestionNumber = takeQuizMutableStateFlow.value.currentQuestionNumber

        if (currentQuestionNumber == 1) {
            clearCurrentQuizAttemptState()
            navigator.pop()
        } else {
            navigator.alert(alertAction = buildLeavingCurrentQuizAlert())
        }
    }

    fun buildLeavingCurrentQuizAlert(): AlertAction {
        return AlertAction(
            title = application.getString(StringIds.leavingCurrentQuiz),
            description = application.getString(StringIds.leavingCurrentQuizDescription),
            confirmButton = AlertConfirmAndDismissButton(buttonText = application.getString(StringIds.yes), onButtonClicked = {
                clearCurrentQuizAttemptState()
                navigator.pop() }
            ),
            dismissButton = AlertConfirmAndDismissButton(buttonText = application.getString(StringIds.no))
        )
    }

    fun buildSelectingAnswerAlert(isLastQuestion: Boolean): AlertAction {
        return AlertAction(
            title = application.getString(StringIds.selectingAAnswer),
            description = application.getString(buildSelectingAnswerDescriptionStringId(isLastQuestion = isLastQuestion)),
            confirmButton = AlertConfirmAndDismissButton(buttonText = application.getString(StringIds.gotIt) ),
            dismissButton = null
        )
    }

    fun buildUnableToContinueWithQuizAlert(): AlertAction {
        return AlertAction(
            title = application.getString(StringIds.unableToContinueWithQuiz),
            description = application.getString(StringIds.unableToContinueWithQuizDescription),
            confirmButton = AlertConfirmAndDismissButton(buttonText = application.getString(StringIds.gotIt)),
            dismissButton = null
        )
    }

    fun selectNewAnswerAndUpdateState(
        currentQuestionNumber: Int,
        questionSize: Int,
        answerIndex: Int
    ) {
        currentQuiz?.questions?.getOrNull(index = currentQuestionNumber - 1)?.answers?.getOrNull(answerIndex)?.let { answer ->
            selectedAnswers.add(answer)

            if (questionSize == currentQuestionNumber) {
                launch { handleLastQuestionAnswered() }
            } else {
                advanceToNextQuestion(currentQuestionNumber = currentQuestionNumber)
            }

            navigator.progress(progressAction = null)

        } ?: run {
            navigator.pop()
            navigator.alert(alertAction = buildUnableToContinueWithQuizAlert())
        }
    }

    private suspend fun handleLastQuestionAnswered() {
        currentQuiz?.let { quiz ->
            savedQuiz = quiz

            val outcome = QuizOutcomeResolver.resolve(quiz = quiz, selectedAnswers = selectedAnswers.toList())

            val savedQuizId = savedQuizRepository.insertQuiz(
                quiz = quiz,
                resultText = outcome.resultText,
                resultImageUrl = outcome.resultImageUrl,
                resultMoreInfo = outcome.resultMoreInfo,
                selectedAnswers = selectedAnswers.toList()
            )

            val route = Constants.NavigationDestinations.QUIZ_RESULT_SCREEN_WITH_PARAMS.replace(
                oldValue = "{${Constants.NamedArguments.QUIZ_ID}}",
                newValue = savedQuizId.toString()
            )
            navigator.navigate(navigationAction = SimpleNavigationAction(destination = route))
        }
    }

    private fun advanceToNextQuestion(currentQuestionNumber: Int) {
        takeQuizMutableStateFlow.update { state ->
            state.copy(
                currentQuestionNumber = currentQuestionNumber + 1,
                selectedAnswerIndex = null
            )
        }
    }

    fun hideProgressAndShowSelectingAnswerAlert(isLastQuestion: Boolean) {
        navigator.progress(progressAction = null)
        navigator.alert(alertAction = buildSelectingAnswerAlert(isLastQuestion = isLastQuestion))
    }

    fun onContinueClicked(currentQuestionNumber: Int, questionSize: Int, selectedAnswerIndex: Int?) {
        navigator.progress(progressAction = ProgressAction())

        selectedAnswerIndex?.let { answerIndex ->
            selectNewAnswerAndUpdateState(
                currentQuestionNumber = currentQuestionNumber,
                questionSize = questionSize,
                answerIndex = answerIndex
            )
        } ?: hideProgressAndShowSelectingAnswerAlert(isLastQuestion = currentQuestionNumber >= questionSize)
    }
}
