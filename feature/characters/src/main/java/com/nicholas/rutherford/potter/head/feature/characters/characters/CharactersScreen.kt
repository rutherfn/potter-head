package com.nicholas.rutherford.potter.head.feature.characters.characters

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import coil.transform.CircleCropTransformation
import com.nicholas.rutherford.potter.head.compose.components.SearchView
import com.nicholas.rutherford.potter.head.compose.ui.theme.getHouseColor
import com.nicholas.rutherford.potter.head.compose.ui.theme.shimmerEffect
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.database.converter.CharacterConverter
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun CharactersScreen(params: CharactersParams) {
    val state = params.state

    when {
        state.isLoading -> ShimmerCharactersContent()
        state.errorType.isValidErrorType() -> EmptyOrErrorContent(
            title = state.errorType.titleId?.let { id -> stringResource(id = id) } ?: "",
            description = state.errorType.descriptionId?.let { id -> stringResource(id = id) } ?: "",
            buttonText = stringResource(id = StringIds.retry),
            onRetryOrClearClicked = params.onRetryClicked
        )
        state.characters.isEmpty() && state.searchQuery.isEmpty() -> EmptyOrErrorContent(
                title = stringResource(id = StringIds.noCharactersYet),
                description = stringResource(id = StringIds.weCouldNotFindAnyCharactersTapRetryToLoadItems),
                buttonText = stringResource(id = StringIds.retry),
                onRetryOrClearClicked = params.onRetryClicked
        )
        else -> CharactersContentWithSearch(state = state, params = params)
    }
}

@Composable
private fun EmptyOrErrorContent(
    title: String,
    description: String,
    buttonText: String,
    onRetryOrClearClicked: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Button(
                onClick = onRetryOrClearClicked,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text(
                    text = buttonText,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
private fun ShimmerCharactersContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        ShimmerSearchView()
        
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = Constants.SHIMMER_CHARACTER_COUNT) {
                ShimmerCharacterCard()
            }
        }
    }
}

@Composable
private fun ShimmerSearchView() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
            
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .shimmerEffect()
            )
        }
    }
}

@Composable
private fun ShimmerCharacterCard() {
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

            Box(modifier = Modifier.size(80.dp).clip(CircleShape).shimmerEffect())

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
                        .width(80.dp)
                        .height(20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .shimmerEffect()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(16.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .shimmerEffect()
                )
            }
        }
    }
}


@Composable
private fun CharactersContentWithSearch(state: CharactersState, params: CharactersParams) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SearchView(
            searchQuery = state.searchQuery,
            onSearchQueryChange = params.onSearchQueryChange,
            onFilterClick = params.onFilterClicked,
            filterCount = state.filterCount,
            onClearClicked = params.onClearClicked,
            placeholderText = stringResource(id = StringIds.searchCharacters)
        )

        if (state.characters.isEmpty() && state.searchQuery.isNotEmpty()) {
            EmptyOrErrorContent(
                title = stringResource(id = StringIds.noCharactersFound),
                description = stringResource(id = StringIds.tryAdjustingYourSearch),
                buttonText = stringResource(id = StringIds.clearSearchResults),
                onRetryOrClearClicked = params.onClearClicked
            )
            EmptySearchResultsContent()
        } else {
            CharactersContent(state = state, params = params)
        }
        
        when {
            state.characters.isEmpty() && state.searchQuery.isNotEmpty() -> {
                EmptySearchResultsContent()
            }
            else -> {
                CharactersContent(state = state, params = params)
            }
        }
    }
}

@Composable
private fun EmptySearchResultsContent() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(id = StringIds.noCharactersFound),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(id = StringIds.tryAdjustingYourSearch),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun CharactersContent(state: CharactersState, params: CharactersParams) {
    val listState = rememberLazyListState()

    LaunchedEffect(key1 = state.hasMoreToLoad, key2 = state.isLoadingMore) {
        snapshotFlow {
            val layoutInfo = listState.layoutInfo
            val totalItems = layoutInfo.totalItemsCount
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            lastVisibleItem to totalItems
        }
            .distinctUntilChanged()
            .filter { (lastVisibleItem, totalItems) ->
                totalItems > 0 && 
                lastVisibleItem >= totalItems - 3 && 
                state.hasMoreToLoad && 
                !state.isLoadingMore
            }
            .collect {
                params.onLoadMore()
            }
    }
    
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(items = state.characters) { character ->
                CharacterCard(
                    character = character,
                    onClick = { params.onCharacterClicked(character.name) },
                    buildCharacterStatusIds = params.buildCharacterStatusIds
                )
            }

            if (state.isLoadingMore) {
                item {
                    LoadingMoreContent()
                }
            }
        }
}

@Composable
private fun LoadingMoreContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun CharacterCard(
    character: CharacterConverter,
    onClick: () -> Unit,
    buildCharacterStatusIds: (CharacterConverter) -> List<Int>
) {
    val statusIds = buildCharacterStatusIds(character)

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
            CharacterAvatar(
                imageUrl = character.image,
                characterName = character.name,
                house = character.house
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = character.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                character.house?.let { house ->
                    if (house.isNotEmpty()) {
                        HouseBadge(house = house)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }

                if (statusIds.isNotEmpty()) {
                    Text(
                        text = buildString {
                            statusIds.forEachIndexed { index, id ->
                                if (index > 0) {
                                    append(", ")
                                }
                                append(stringResource(id = id))
                            }},
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun CharacterAvatar(imageUrl: String?, characterName: String, house: String?) {
    val initial = characterName.firstOrNull()?.uppercase() ?: ""
    val houseColor = if (house != null && house.isNotEmpty()) {
        getHouseColor(house)
    } else {
        MaterialTheme.colorScheme.primary
    }
    val backgroundColor = houseColor.copy(alpha = if (house != null && house.isNotEmpty()) 0.3f else 0.2f)
    val textColor = houseColor
    
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            .background(color = backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
        
        imageUrl?.let { url ->
            AsyncImage(
                model = ImageRequest.Builder(context = androidx.compose.ui.platform.LocalContext.current)
                    .data(url)
                    .size(Size(80, 80))
                    .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = characterName,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        }
    }
}

@Composable
private fun HouseBadge(house: String) {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = getHouseColor(house = house).copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(
            text = house,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            color = getHouseColor(house),
            fontSize = 12.sp
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun CharacterScreenPreview() {
    MaterialTheme {
        CharactersScreen(
            params = CharactersParams(
                state = CharactersState(
                    characters = listOf(
                        CharacterConverter(
                            name = "Harry Potter",
                            alternateNames = emptyList(),
                            species = "human",
                            gender = "male",
                            house = "Gryffindor",
                            dateOfBirth = "31-07-1980",
                            yearOfBirth = 1980,
                            isWizard = true,
                            ancestry = "half-blood",
                            eyeColour = "green",
                            hairColour = "black",
                            wandConverter = null,
                            patronus = "stag",
                            isHogwartsStudent = true,
                            isHogwartsStaff = false,
                            actor = "Daniel Radcliffe",
                            alternateActors = emptyList(),
                            isAlive = true,
                            image = null
                        ),
                        CharacterConverter(
                            name = "Hermione Granger",
                            alternateNames = emptyList(),
                            species = "human",
                            gender = "female",
                            house = "Gryffindor",
                            dateOfBirth = "19-09-1979",
                            yearOfBirth = 1979,
                            isWizard = true,
                            ancestry = "muggle-born",
                            eyeColour = "brown",
                            hairColour = "brown",
                            wandConverter = null,
                            patronus = "otter",
                            isHogwartsStudent = true,
                            isHogwartsStaff = false,
                            actor = "Emma Watson",
                            alternateActors = emptyList(),
                            isAlive = true,
                            image = null
                        )
                    )
                ),
                onCharacterClicked = {},
                onRetryClicked = {},
                onLoadMore = {},
                buildCharacterStatusIds = { _ -> emptyList() },
                onSearchQueryChange = {},
                onClearClicked = {},
                onFilterClicked = {}
            )
        )
    }
}