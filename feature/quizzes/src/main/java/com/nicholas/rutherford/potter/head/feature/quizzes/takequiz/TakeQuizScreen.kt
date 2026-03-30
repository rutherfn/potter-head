package com.nicholas.rutherford.potter.head.feature.quizzes.takequiz

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.compose.components.SelectionIndicator
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.entity.AnswerEntity

/**
 * Composable screen for displaying the quiz section with a list of questions and answers.
 *
 * @param params The parameters containing the [TakeQuizState] with quiz data
 *               and interaction callbacks.
 *
 * @see TakeQuizState for the state structure
 * @see TakeQuizParams for parameter details
 *
 * @author Nicholas Rutherford
 */
@Composable
fun TakeQuizScreen(params: TakeQuizParams) {
    val state = params.state
    val currentQuestion = state.questions.getOrNull(index = (state.currentQuestionNumber - 1).coerceIn(0, (state.questions.size - 1).coerceAtLeast(0)))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
            .verticalScroll(state = rememberScrollState())
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (currentQuestion != null) {
                QuestionTitle(
                    questionNumber = state.currentQuestionNumber,
                    questionSize = state.questionSize,
                    questionText = currentQuestion.text
                )

                AnswerList(
                    answers = currentQuestion.answers,
                    selectedAnswerIndex = state.selectedAnswerIndex,
                    onAnswerSelected = params.onAnswerSelected
                )

                Button(
                    onClick = {
                        params.onContinueClicked.invoke(
                            state.currentQuestionNumber,
                            state.questionSize,
                            state.selectedAnswerIndex
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Continue",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun QuestionTitle(
    questionNumber: Int,
    questionSize: Int,
    questionText: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(id = StringIds.questionXOfY, questionNumber, questionSize),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = questionText,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun AnswerList(
    answers: List<AnswerEntity>,
    selectedAnswerIndex: Int?,
    onAnswerSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        answers.forEachIndexed { index, answer ->
            AnswerOption(
                text = answer.text,
                isSelected = selectedAnswerIndex == index,
                onClick = { onAnswerSelected(index) }
            )
        }
    }
}

@Composable
private fun AnswerOption(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val containerColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "containerColor"
    )
    val borderColor by animateColorAsState(
        targetValue = if (isSelected) {
            MaterialTheme.colorScheme.primary
        } else {
            MaterialTheme.colorScheme.outlineVariant
        },
        label = "borderColor"
    )
    val borderWidth by animateFloatAsState(
        targetValue = if (isSelected) 2f else 1f,
        label = "borderWidth"
    )
    val shape = RoundedCornerShape(16.dp)

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = borderWidth.dp,
                color = borderColor,
                shape = shape
            )
            .clip(shape)
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onClick
            ),
        shape = shape,
        color = containerColor,
        tonalElevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            SelectionIndicator(isSelected = isSelected)
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = if (isSelected) {
                    FontWeight.Medium
                } else {
                    FontWeight.Normal
                }
            )
        }
    }
}
