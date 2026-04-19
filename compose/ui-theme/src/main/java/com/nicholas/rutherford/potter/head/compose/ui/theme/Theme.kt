package com.nicholas.rutherford.potter.head.compose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme =
    darkColorScheme(
        primary = Orange80,
        secondary = Red80,
        tertiary = OrangeGrey80,
        background = SurfaceDark,
        surface = PotterGrey,
        surfaceVariant = SurfaceVariant,
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
        surfaceVariant = SurfaceVariantLight,
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
