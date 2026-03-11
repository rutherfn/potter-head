package com.nicholas.rutherford.potter.head.feature.spells

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.compose.components.EmptyOrErrorContent
import com.nicholas.rutherford.potter.head.compose.components.SearchView
import com.nicholas.rutherford.potter.head.compose.ui.theme.shimmerEffect
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.DataErrorType
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.core.isValidErrorType
import com.nicholas.rutherford.potter.head.database.converter.SpellConverter

@Composable
fun SpellsScreen(params: SpellsParams) {
    val state = params.state

    when {
        state.isLoading && state.spells.isEmpty() && state.searchQuery.isEmpty() -> ShimmerSpellsContent()
        state.errorType.isValidErrorType() && state.searchQuery.isEmpty() -> {
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
        state.spells.isEmpty() && !state.isLoading && state.searchQuery.isEmpty() -> EmptyOrErrorContent(
            title = stringResource(id = StringIds.noSpellsYet),
            description = stringResource(id = StringIds.weCouldNotFindAnySpellsTapRetryToLoadItems),
            buttonText = stringResource(id = StringIds.retry),
            onButtonClicked = params.onRetryClicked
        )
        else -> SpellsContentWithSearch(params = params, state = state)
    }
}

@Composable
private fun ShimmerSpellsContent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(count = Constants.SHIMMER_CHARACTER_COUNT) {
                ShimmerSpellCard()
            }
        }
    }
}

@Composable
private fun ShimmerSpellCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(24.dp)
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

@Composable
private fun SpellsContentWithSearch(params: SpellsParams, state: SpellsState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        SearchView(
            searchQuery = state.searchQuery,
            onSearchQueryChange = params.onSearchQueryChange,
            onClearClicked = params.onClearClicked,
            placeholderText = stringResource(id = StringIds.searchSpells)
        )

        if (state.spells.isEmpty() && state.searchQuery.isNotEmpty()) {
            EmptyOrErrorContent(
                title = stringResource(id = StringIds.noSpellsFound),
                description = stringResource(id = StringIds.tryAdjustingYourSearch),
                buttonText = stringResource(id = StringIds.clearSearchResults),
                onButtonClicked = params.onClearClicked
            )
        } else {
            SpellsContent(state = state)
        }
    }
}

@Composable
private fun SpellsContent(state: SpellsState) {
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(items = state.spells) { spell ->
            SpellItem(spell = spell)
        }
    }
}

@Composable
private fun SpellItem(spell: SpellConverter) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = spell.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = spell.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = MaterialTheme.typography.bodyMedium.lineHeight * 1.2
            )
        }
    }
}