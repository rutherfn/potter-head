package com.nicholas.rutherford.potter.head.feature.quizzes.quizdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size

/**
 * Composable screen for displaying detailed information about a quiz.
 *
 * @param params The parameters containing the [QuizDetailState] with quiz data
 *               and interaction callbacks.
 *
 * @see QuizDetailState for the state structure
 * @see QuizDetailParams for parameter details
 *
 * @author Nicholas Rutherford
 */
@Composable
fun QuizDetailScreen(params: QuizDetailParams) {
    val state = params.state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {

        QuizHeroImage(
            imageUrl = state.imageUrl,
            quizTitle = state.title
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = state.title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = state.description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight * 1.4
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { /* TODO: Start quiz functionality */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Start Quiz",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
private fun QuizHeroImage(
    imageUrl: String,
    quizTitle: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(MaterialTheme.colorScheme.surfaceVariant),
        contentAlignment = Alignment.Center
    ) {
        if (imageUrl.isNotEmpty()) {
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(imageUrl)
                    .size(Size(800, 560))
                    .build(),
                contentDescription = quizTitle,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = quizTitle.firstOrNull()?.uppercase() ?: "",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}
