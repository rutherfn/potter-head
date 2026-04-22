package com.nicholas.rutherford.potter.head.feature.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.nicholas.rutherford.potter.head.compose.components.EmptyOrErrorContent
import com.nicholas.rutherford.potter.head.compose.ui.theme.shimmerEffect
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.core.isValidErrorType
import com.nicholas.rutherford.potter.head.core.safeLet
import com.nicholas.rutherford.potter.head.feature.quizzes.ext.QuizzesConverter

/**
 * Main composable screen for displaying quizzes.
 *
 * This screen provides a filtering interface that allows users to view:
 * - Available Quizzes: Quizzes that can be taken
 * - Submitted Quizzes: Quizzes that have been completed
 *
 * @param params The parameters containing quiz data, state, and interaction callbacks.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun QuizzesScreen(params: QuizzesParams) {
    val state = params.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {

        if (state.shouldShowFilterChips) {
            QuizFilterChips(
                state = state,
                onFilterItemClicked = params.onFilterItemClicked
            )
        }

        when {
            state.isLoading && state.quizzes.isEmpty() && state.selectedFilterIndex == 0 -> ShimmerQuizzesContent()
            state.errorType.isValidErrorType() && state.selectedFilterIndex == 0 -> {
                val title = when (val error = state.errorType) {
                    is DataErrorType.FailedToFetchData -> {
                        error.titleId?.let { id -> stringResource(id = id, error.dataType) } ?: ""
                    }
                    else -> error.titleId?.let { id -> stringResource(id = id) } ?: ""
                }
                val description = when (val error = state.errorType) {
                    is DataErrorType.FailedToFetchData -> {
                        error.descriptionId?.let { id -> stringResource(id = id, error.dataType) } ?: ""
                    }
                    else -> error.descriptionId?.let { id -> stringResource(id = id) } ?: ""
                }
                EmptyOrErrorContent(
                    title = title,
                    description = description,
                    buttonText = stringResource(id = StringIds.retry),
                    onButtonClicked = params.onRetryClicked
                )
            }
            state.quizzes.isEmpty() && !state.isLoading && state.selectedFilterIndex == 0 -> EmptyOrErrorContent(
                title = stringResource(id = StringIds.noQuizzesYet),
                description = stringResource(id = StringIds.weCouldNotFindAnyQuizzesTapRetryToLoadItems),
                buttonText = stringResource(id = StringIds.retry),
                onButtonClicked = params.onRetryClicked
            )
            else -> {
                QuizzesContent(
                    state = state,
                    onSavedQuizClicked = params.onSavedQuizClicked,
                    onQuizClicked = params.onQuizClicked
                )
            }
        }
    }
}

@Composable
private fun QuizFilterChips(state: QuizzesState, onFilterItemClicked: (index: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        state.filterTypes.forEachIndexed { index, filter ->
            val isSelected = index == state.selectedFilterIndex
            
            FilterChip(
                selected = isSelected,
                onClick = { onFilterItemClicked(index) },
                label = { Text(text = filter) },
                modifier = Modifier.padding(end = if (index < state.filterTypes.size - 1) 12.dp else 0.dp),
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
private fun ShimmerQuizzesContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = Constants.SHIMMER_CHARACTER_COUNT) {
                ShimmerQuizCard()
            }
        }
    }
}

@Composable
private fun ShimmerQuizCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(100.dp, 80.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(20.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}

@Composable
private fun QuizzesContent(
    state: QuizzesState,
    onSavedQuizClicked: (quizId: Long) -> Unit,
    onQuizClicked: (title: String, description: String, imageUrl: String) -> Unit
) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = state.quizzes) { quiz ->
            QuizItem(
                quiz = quiz,
                onClick = {
                    if (state.selectedFilterIndex == 0) {
                        onQuizClicked(quiz.title, quiz.longDescription, quiz.imageUrl)
                    } else {
                        onSavedQuizClicked(quiz.id)
                    }
                }
            )
        }
    }
}

@Composable
private fun QuizItem(
    quiz: QuizzesConverter,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            QuizImage(
                imageUrl = quiz.imageUrl,
                quizTitle = quiz.title
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = quiz.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                safeLet(quiz.timestampOfLastLogged, quiz.quizResult) { timestamp, result ->
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(
                            text = stringResource(id = StringIds.quizListSubmittedResultLine, result),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = stringResource(id = StringIds.quizListSubmittedCompletedLine, timestamp),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                } ?: run {
                    Text(
                        text = quiz.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun QuizImage(imageUrl: String, quizTitle: String) {
    Box(
        modifier = Modifier
            .size(width = 100.dp, height = 80.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
    ) {
        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl)
                    .size(Size(200, 160))
                    .build(),
                contentDescription = quizTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quizTitle.firstOrNull()?.uppercase() ?: "",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
