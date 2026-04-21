package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme
import kotlinx.coroutines.delay

/**
 * Card layout for a hero image (or placeholder), primary headline, supporting copy, and an optional
 * labeled highlight (for example a quiz outcome). Content is revealed in sequence when the card
 * appears or when its inputs change.
 *
 * @param imageUrl URL for the hero image; when blank, a gradient placeholder with an icon is shown.
 * @param imageContentDescription Content description for the hero image when [imageUrl] is non-blank.
 * @param headline Primary title, centered and emphasized.
 * @param supportingText Secondary line under the headline, centered and muted.
 * @param highlightLabel Short label shown above [highlightValue]; ignored when [highlightValue] is blank.
 * @param highlightValue Optional emphasized value; when blank, the divider and highlight section are omitted.
 * @param modifier [Modifier] for the root [Card].
 *
 * @author Nicholas Rutherford
 */
@Composable
fun HeroSummaryCard(
    imageUrl: String,
    imageContentDescription: String,
    headline: String,
    supportingText: String,
    highlightLabel: String,
    highlightValue: String,
    modifier: Modifier = Modifier
) {
    val cardShape = RoundedCornerShape(24.dp)

    var showHeadline by remember(imageUrl, headline, supportingText, highlightValue) {
        mutableStateOf(false)
    }
    var showSupporting by remember(imageUrl, headline, supportingText, highlightValue) {
        mutableStateOf(false)
    }
    var showHero by remember(imageUrl, headline, supportingText, highlightValue) {
        mutableStateOf(false)
    }
    var showHighlight by remember(imageUrl, headline, supportingText, highlightValue) {
        mutableStateOf(false)
    }

    LaunchedEffect(imageUrl, headline, supportingText, highlightValue) {
        showHeadline = true
        delay(timeMillis = 90)
        showSupporting = true
        delay(timeMillis = 140)
        showHero = true
        delay(timeMillis = 160)
        showHighlight = highlightValue.isNotEmpty()
    }

    val imageScaleTarget = if (showHero) {
        1f
    } else {
        0.88f
    }
    val imageAlphaTarget = if (showHero) {
        1f
    } else {
        0f
    }

    val imageScale by animateFloatAsState(
        targetValue = imageScaleTarget,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "heroSummaryImageScale"
    )
    val imageAlpha by animateFloatAsState(
        targetValue = imageAlphaTarget,
        animationSpec = tween(durationMillis = 420),
        label = "heroSummaryImageAlpha"
    )

    val onSurface = MaterialTheme.colorScheme.onSurface
    val onSurfaceVariant = MaterialTheme.colorScheme.onSurfaceVariant

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = cardShape,
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HeroSummaryCardTopImage(
                imageUrl = imageUrl,
                contentDescription = imageContentDescription,
                placeholderTint = onSurfaceVariant,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height = 200.dp)
                    .graphicsLayer {
                        scaleX = imageScale
                        scaleY = imageScale
                        alpha = imageAlpha
                    }
                    .clip(shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AnimatedVisibility(
                    visible = showHeadline,
                    enter = fadeIn(animationSpec = tween(durationMillis = 320)) +
                        slideInVertically(animationSpec = tween(durationMillis = 320)) { fullHeight ->
                            fullHeight / 5
                        },
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Text(
                        text = headline,
                        style = MaterialTheme.typography.headlineSmall.copy(
                            lineHeight = 32.sp,
                            letterSpacing = 0.2.sp
                        ),
                        fontWeight = FontWeight.Bold,
                        color = onSurface,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedVisibility(
                    visible = showSupporting,
                    enter = fadeIn(animationSpec = tween(durationMillis = 340)) +
                        slideInVertically(animationSpec = tween(durationMillis = 340)) { fullHeight ->
                            fullHeight / 6
                        },
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Text(
                        text = supportingText,
                        style = MaterialTheme.typography.bodyLarge.copy(lineHeight = 26.sp),
                        color = onSurfaceVariant,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
                AnimatedVisibility(
                    visible = showHighlight,
                    enter = fadeIn(animationSpec = tween(durationMillis = 400)) +
                        slideInVertically(animationSpec = tween(durationMillis = 400)) { fullHeight ->
                            fullHeight / 4
                        },
                    exit = fadeOut() + slideOutVertically()
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(space = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(20.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant,
                            border = BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outlineVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 20.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(space = 10.dp)
                            ) {
                                Text(
                                    text = highlightLabel,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.SemiBold,
                                    color = onSurface,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                                Text(
                                    text = highlightValue,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        lineHeight = 36.sp
                                    ),
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

/**
 * Hero strip: loads [imageUrl] with Coil when set, otherwise a gradient placeholder and icon.
 *
 * @param imageUrl Remote image URL; blank selects the placeholder.
 * @param contentDescription TalkBack description when the image is shown.
 * @param placeholderTint Tint for the placeholder icon.
 * @param modifier [Modifier] applied to the image or placeholder [Box].
 */
@Composable
private fun HeroSummaryCardTopImage(
    imageUrl: String,
    contentDescription: String,
    placeholderTint: Color,
    modifier: Modifier
) {
    if (imageUrl.isNotBlank()) {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = ContentScale.Crop
        )
    } else {
        Box(
            modifier = modifier.background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    )
                )
            ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.AutoAwesome,
                contentDescription = null,
                modifier = Modifier.size(size = 72.dp),
                tint = placeholderTint.copy(alpha = 0.9f)
            )
        }
    }
}

@Preview(showBackground = true, name = "HeroSummaryCard — with highlight (light)")
@Composable
private fun HeroSummaryCardPreviewWithHighlightLight() {
    PotterHeadTheme(darkTheme = false) {
        HeroSummaryCard(
            imageUrl = "",
            imageContentDescription = "",
            headline = "Sorting complete",
            supportingText = "Here is where the Hat placed you.",
            highlightLabel = "Your house",
            highlightValue = "Gryffindor",
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview(showBackground = true, name = "HeroSummaryCard — no highlight (dark)")
@Composable
private fun HeroSummaryCardPreviewNoHighlightDark() {
    PotterHeadTheme(darkTheme = true) {
        HeroSummaryCard(
            imageUrl = "",
            imageContentDescription = "",
            headline = "Patronus quiz",
            supportingText = "Answer the questions to reveal your guardian.",
            highlightLabel = "",
            highlightValue = "",
            modifier = Modifier.padding(16.dp)
        )
    }
}
