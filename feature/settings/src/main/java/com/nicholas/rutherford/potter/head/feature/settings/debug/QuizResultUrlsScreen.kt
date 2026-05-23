package com.nicholas.rutherford.potter.head.feature.settings.debug

import androidx.compose.foundation.clickable
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DrawableIds
import com.nicholas.rutherford.potter.head.core.StringIds

@Composable
fun QuizResultUrlsScreen(params: QuizResultUrlsParams) {
    val state = params.state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item(key = Constants.QuizResultUrlsLazyColumnKeys.SCREEN_DESCRIPTION) {
            Text(
                text = stringResource(id = StringIds.quizResultUrlsScreenDescription),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }

        state.sections.forEach { section ->
            item(key = Constants.QuizResultUrlsLazyColumnKeys.quizHeader(quizId = section.quizId)) {
                QuizResultUrlItem(
                    params = section.header,
                    onViewUrlClicked = params.onViewUrlClicked,
                )
            }

            item(key = Constants.QuizResultUrlsLazyColumnKeys.quizDivider(quizId = section.quizId)) {
                Column(
                    modifier = Modifier.padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Text(
                        text = "Result images",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }

            if (section.resultItems.isEmpty()) {
                item(key = Constants.QuizResultUrlsLazyColumnKeys.quizEmpty(quizId = section.quizId)) {
                    Text(
                        text = "No result images configured.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            } else {
                items(
                    items = section.resultItems,
                    key = { resultItem ->
                        Constants.QuizResultUrlsLazyColumnKeys.resultItem(
                            quizId = section.quizId,
                            resultLabel = resultItem.resultLabel,
                        )
                    },
                ) { resultItem ->
                    QuizResultUrlItem(
                        params = resultItem,
                        onViewUrlClicked = params.onViewUrlClicked,
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizResultUrlItem(
    params: QuizResultUrlItemParams,
    onViewUrlClicked: (String) -> Unit,
) {
    val placeholder = painterResource(id = DrawableIds.icPlaceholder)
    val imageUrl = params.imageUrl.trim()
    val imageModel = imageUrl.ifBlank { params.fallbackImageUrl.trim() }.takeIf { it.isNotBlank() }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = params.resultLabel,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center,
            ) {
                if (imageModel == null) {
                    Image(
                        painter = placeholder,
                        contentDescription = params.resultLabel,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                    )
                } else {
                    AsyncImage(
                        model = imageModel,
                        contentDescription = params.resultLabel,
                        contentScale = ContentScale.Crop,
                        placeholder = placeholder,
                        error = placeholder,
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }

            Text(
                text = imageUrl.ifBlank { params.emptyUrlMessage },
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis,
            )

            if (imageUrl.isNotBlank()) {
                Text(
                    text = stringResource(id = StringIds.viewUrl),
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = Color(0xFF1E88E5),
                        textDecoration = TextDecoration.Underline,
                    ),
                    modifier = Modifier.clickable { onViewUrlClicked(imageUrl) },
                )
            }
        }
    }
}
