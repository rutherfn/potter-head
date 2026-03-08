package com.nicholas.rutherford.potter.head.feature.characters.characterdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.nicholas.rutherford.potter.head.compose.components.HouseBadge
import com.nicholas.rutherford.potter.head.compose.components.InfoCard
import com.nicholas.rutherford.potter.head.compose.components.data.InfoItem
import com.nicholas.rutherford.potter.head.compose.ui.theme.getHouseColor
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter

/**
 * Composable screen for displaying detailed information about a character.
 *
 * @param params The parameters containing the [CharacterDetailState] with character data
 *               and processed information items for display.
 *
 * @see CharacterDetailState for the state structure
 * @see CharacterDetailParams for parameter details
 * @see InfoCard for the card component used to display information sections
 *
 * @author Nicholas Rutherford
 */
@Composable
fun CharacterDetailScreen(params: CharacterDetailParams) {
    val state = params.state

    state.character?.let { character ->
        CharacterDetailContent(
            character = character,
            basicInfoItems = state.basicInfoItems,
            personalInfoItems = state.personalInfoItems,
            hogwartsInfoItems = state.hogwartsInfoItems,
            additionalInfoItems = state.additionalInfoItems
        )
    } ?: EmptyCharacterContent()
}

@Composable
private fun EmptyCharacterContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(id = StringIds.characterNotFound),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun CharacterDetailContent(
    character: CharacterConverter,
    basicInfoItems: List<CharacterDetailInfoItem>,
    personalInfoItems: List<CharacterDetailInfoItem>,
    hogwartsInfoItems: List<CharacterDetailInfoItem>,
    additionalInfoItems: List<CharacterDetailInfoItem>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        CharacterImageSection(
            imageUrl = character.image,
            characterName = character.name,
            house = character.house
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                character.house?.let { house ->
                    if (house.isNotEmpty()) {
                        HouseBadge(house = house)
                    }
                }
            }

            if (basicInfoItems.isNotEmpty()) {
                InfoCard(
                    title = stringResource(id = StringIds.basicInformation),
                    items = basicInfoItems.map { item ->
                        InfoItem(
                            labelId = item.labelId,
                            value = item.value
                        )
                    }
                )
            }

            if (personalInfoItems.isNotEmpty()) {
                InfoCard(
                    title = stringResource(id = StringIds.personalDetails),
                    items = personalInfoItems.map { item ->
                        InfoItem(
                            labelId = item.labelId,
                            value = item.value
                        )
                    }
                )
            }

            if (hogwartsInfoItems.isNotEmpty()) {
                InfoCard(
                    title = stringResource(id = StringIds.hogwartsInformation),
                    items = hogwartsInfoItems.map { item ->
                        InfoItem(
                            labelId = item.labelId,
                            value = item.value
                        )
                    }
                )
            }

            if (additionalInfoItems.isNotEmpty()) {
                InfoCard(
                    title = stringResource(id = StringIds.additionalInformation),
                    items = additionalInfoItems.map { item ->
                        InfoItem(
                            labelId = item.labelId,
                            value = item.value
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun CharacterImageSection(
    imageUrl: String?,
    characterName: String,
    house: String?
) {
    val houseColor = if (house != null && house.isNotEmpty()) {
        getHouseColor(house)
    } else {
        MaterialTheme.colorScheme.primary
    }
    val backgroundColor = houseColor.copy(alpha = 0.1f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            imageUrl?.takeIf { it.isNotBlank() }?.let { url ->
                AsyncImage(
                    model = ImageRequest.Builder(context =LocalContext.current)
                        .data(url)
                        .size(Size(400, 400))
                        .transformations(CircleCropTransformation())
                        .build(),
                    contentDescription = characterName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape)
                )
            } ?: run {
                Text(
                    text = characterName.firstOrNull()?.uppercase() ?: "",
                    style = MaterialTheme.typography.displayLarge,
                    fontWeight = FontWeight.Bold,
                    color = houseColor
                )
            }
        }
    }
}

