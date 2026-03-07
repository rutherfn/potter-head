package com.nicholas.rutherford.potter.head.compose.ui.theme

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.nicholas.rutherford.potter.head.core.Constants

/**
 * Extension function that applies a shimmer effect to a modifier.
 * Creates an animated gradient that moves across the composable to simulate a loading shimmer.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun Modifier.shimmerEffect(): Modifier {
    val shimmerColors = listOf(
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f),
        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateAnim by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1200,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer_translate"
    )

    return this.then(
        Modifier.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(translateAnim - 300f, translateAnim - 300f),
                end = Offset(translateAnim, translateAnim)
            )
        )
    )
}

/**
 * Returns the color associated with a Hogwarts house name.
 *
 * @param house The house name (case-insensitive).
 * @return The corresponding house color, or the theme's primary color if the house is not recognized.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun getHouseColor(house: String): Color {
    return when (house.lowercase()) {
        Constants.GRYFFINDOR_HOUSE -> GryffindorRed
        Constants.SLYTHERIN_HOUSE -> SlytherinGreen
        Constants.RAVENCLAW_HOUSE -> RavenclawBlue
        Constants.HUFFLEPUFF_HOUSE -> HufflepuffYellow
        else -> MaterialTheme.colorScheme.primary
    }
}

/**
 * Returns the color associated with a species name.
 *
 * @param species The species name (case-insensitive).
 * @return The corresponding species color, or the theme's primary color if the species is not recognized.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun getSpeciesColor(species: String): Color {
    return when (species.lowercase()) {
        Constants.SPECIES_HUMAN -> SpeciesHuman
        Constants.SPECIES_DRAGON -> SpeciesDragon
        Constants.SPECIES_GHOST -> SpeciesGhost
        Constants.SPECIES_GOBLIN -> SpeciesGoblin
        Constants.SPECIES_GIANT -> SpeciesGiant
        Constants.SPECIES_PHOENIX -> SpeciesPhoenix
        Constants.SPECIES_WEREWOLF -> SpeciesWerewolf
        Constants.SPECIES_VAMPIRE -> SpeciesVampire
        Constants.SPECIES_CENTAUR -> SpeciesCentaur
        Constants.SPECIES_HIPPOGRIFF -> SpeciesHippogriff
        Constants.SPECIES_HOUSE_ELF -> SpeciesHouseElf
        Constants.SPECIES_OWL -> SpeciesOwl
        Constants.SPECIES_SNAKE -> SpeciesSnake
        Constants.SPECIES_SERPENT -> SpeciesSerpent
        Constants.SPECIES_CAT -> SpeciesCat
        Constants.SPECIES_DOG -> SpeciesDog
        Constants.SPECIES_THREE_HEADED_DOG -> SpeciesThreeHeadedDog
        Constants.SPECIES_TOAD -> SpeciesToad
        Constants.SPECIES_POLTERGEIST -> SpeciesPoltergeist
        Constants.SPECIES_CEPHALOPOD -> SpeciesCephalopod
        Constants.SPECIES_SELKIE -> SpeciesSelkie
        Constants.SPECIES_ACROMANTULA -> SpeciesAcromantula
        Constants.SPECIES_PYGMY_PUFF -> SpeciesPygmyPuff
        Constants.SPECIES_HAT -> SpeciesHat
        Constants.SPECIES_HALF_GIANT -> SpeciesHalfGiant
        Constants.SPECIES_HALF_HUMAN -> SpeciesHalfHuman
        else -> MaterialTheme.colorScheme.primary
    }
}

