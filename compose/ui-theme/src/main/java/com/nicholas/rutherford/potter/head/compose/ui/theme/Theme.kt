package com.nicholas.rutherford.potter.head.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
    darkColorScheme(
        primary = Orange80,
        secondary = Red80,
        tertiary = OrangeGrey80,
        background = SurfaceDark,
        surface = PotterGrey,
        surfaceVariant = Color(0xFF2B2B2B),
        onPrimary = TextOnPrimary,
        onSecondary = TextOnPrimary,
        onTertiary = TextOnPrimary,
        onBackground = TextOnDark,
        onSurface = TextOnDark,
        onSurfaceVariant = TextOnDark,
        error = PotterRed,
        onError = TextOnPrimary
    )

private val LightColorScheme =
    lightColorScheme(
        primary = OrangePrimary,
        secondary = RedPrimary,
        tertiary = OrangeGrey40,
        background = SurfaceLight,
        surface = PotterWhite,
        surfaceVariant = Color(0xFFF0F0F0),
        onPrimary = TextOnPrimary,
        onSecondary = TextOnPrimary,
        onTertiary = TextOnPrimary,
        onBackground = TextPrimary,
        onSurface = TextPrimary,
        onSurfaceVariant = TextSecondary,
        error = PotterRed,
        onError = TextOnPrimary
    )

@Composable
fun PotterHeadTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (!darkTheme) {
            LightColorScheme
        } else {
            DarkColorScheme
        },
        typography = Typography,
        content = content
    )
}