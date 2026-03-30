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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay

/**
 * A reusable card with a top hero image (or placeholder), a primary headline, supporting text,
 * and an optional labeled highlight section (e.g. an outcome or score).
 *
 * **Animation behavior**
 *
 * On first display (and whenever [imageUrl], [headline], [supportingText], or [highlightValue]
 * change), content is revealed in sequence:
 *
 * 1. **Headline** — fades in and slides up slightly ([AnimatedVisibility] + [tween]).
 * 2. **Supporting text** — same pattern, started after a short [delay] so lines read in order.
 * 3. **Hero image** — scales from slightly smaller to full size ([spring]) while fading in ([tween])
 *    via [graphicsLayer]; gives a light “settle” without blocking text.
 * 4. **Highlight block** — only if [highlightValue] is non-blank; appears last with fade + slide,
 *    separated by a [HorizontalDivider].
 *
 * Visibility flags reset when the remembered keys change, so navigating to new content replays
 * the sequence.
 *
 * @param imageUrl URL for the hero image; if blank, a neutral gradient placeholder with icon is shown.
 * @param imageContentDescription Accessibility description for the hero image when [imageUrl] is used.
 * @param headline Primary title (centered, high emphasis).
 * @param supportingText Secondary line under the headline (centered, muted color).
 * @param highlightLabel Short label above the highlight value; ignored when [highlightValue] is blank.
 * @param highlightValue Optional emphasized value; when blank, the divider and highlight [Surface] are not shown.
 * @param modifier Modifier for the root [Card].
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

    // One visibility flag per animated region; keys reset state when content identity changes.
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

    // Stagger reveals so users read top-to-bottom; image animates in parallel with the later text beats.
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
                        // Uses theme surfaceVariant (light grey in light mode); label uses onSurface for contrast.
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
 * Loads [imageUrl] with Coil when present; otherwise draws a neutral placeholder (gradient + icon).
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
