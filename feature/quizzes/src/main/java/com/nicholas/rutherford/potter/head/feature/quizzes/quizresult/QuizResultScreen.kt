package com.nicholas.rutherford.potter.head.feature.quizzes.quizresult

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.nicholas.rutherford.potter.head.compose.components.HeroSummaryCard
import com.nicholas.rutherford.potter.head.compose.components.SelectionIndicator
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.entity.SavedAnswerItem

/**
 * Composable screen for displaying results for a quiz.
 *
 * @param params The parameters containing the [QuizResultState] with quiz result data
 *               and interaction callbacks.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun QuizResultScreen(params: QuizResultParams) {
    val state = params.state

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        HeroSummaryCard(
            imageUrl = state.quizImageUrl,
            imageContentDescription = state.quizTitle,
            headline = stringResource(id = StringIds.quizCompleted),
            supportingText = stringResource(StringIds.congratsYouCompletedX, state.quizTitle),
            highlightLabel = stringResource(id = StringIds.yourResult),
            highlightValue = state.resultText
        )

        if (state.showDetailedResults) {
            DetailedResultsSection(state = state)

            OutlinedButton(
                onClick = params.onHideResultsClicked,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "Hide Quiz Results")
            }
        } else {
            OutlinedButton(
                onClick = params.onViewResultsClicked,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "View Quiz Results")
            }
        }

        Button(
            onClick = params.onContinueClicked,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {
            Text(text = "Continue")
        }
    }
}

@Composable
private fun DetailedResultsSection(state: QuizResultState) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Your Answers",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        state.questions.forEachIndexed { qIndex, question ->
            Text(
                text = "${qIndex + 1}. ${question.questionText}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold
            )
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(space = 10.dp)
            ) {
                question.answers.forEach { answer ->
                    ResultAnswerRow(answer = answer)
                }
            }
        }
    }
}

@Composable
private fun ResultAnswerRow(answer: SavedAnswerItem) {
    val isUserSelection = answer.isSelected
    val containerColor by animateColorAsState(
        targetValue = if (isUserSelection) {
            MaterialTheme.colorScheme.primaryContainer
        } else {
            MaterialTheme.colorScheme.surface
        },
        label = "resultAnswerContainer"
    )
    val borderColor by animateColorAsState(
        targetValue = when {
            isUserSelection -> MaterialTheme.colorScheme.primary
            answer.isCorrect -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.outlineVariant
        },
        label = "resultAnswerBorder"
    )
    val borderWidth by animateFloatAsState(
        targetValue = if (isUserSelection || answer.isCorrect) 2f else 1f,
        label = "resultAnswerBorderWidth"
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
            .clip(shape),
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
            SelectionIndicator(isSelected = isUserSelection)
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = answer.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = if (isUserSelection) {
                        FontWeight.Medium
                    } else {
                        FontWeight.Normal
                    }
                )
                val statusParts = buildList {
                    if (isUserSelection) {
                        add("Selected")
                    }
                    if (answer.isCorrect) {
                        add("Correct answer")
                    }
                }
                if (statusParts.isNotEmpty()) {
                    Text(
                        text = statusParts.joinToString(separator = " · "),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}
