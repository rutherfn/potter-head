package com.nicholas.rutherford.potter.head.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.core.Constants
import com.nicholas.rutherford.potter.head.core.StringIds
import com.nicholas.rutherford.potter.head.core.theme.ThemePreference

@Composable
fun SettingsScreen(params: SettingsParams) {
    val state = params.state
    val uriHandler = LocalUriHandler.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(space = 4.dp)
    ) {
        item {
            Text(
                text = stringResource(id = StringIds.appearance),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            ThemeChoiceRow(
                themeOptions = state.themeOptions,
                selectedTheme = state.selectedTheme,
                onThemeSelected = params.onThemePreferenceSelected
            )
        }

        item { Spacer(modifier = Modifier.height(height = 16.dp)) }

        item {
            Text(
                text = stringResource(id = StringIds.quizzes),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            ShuffleAnswersRow(
                checked = state.shouldShuffleAnswerOrderChecked,
                onCheckedChange = { value -> params.onShuffleAnswerOrderCheckedChanged(value) }
            )
        }

        item { Spacer(modifier = Modifier.height(height = 16.dp)) }

        item {
            Text(
                text = stringResource(id = StringIds.data),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            Column(
                modifier = Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(space = 4.dp)
            ) {
                TextButton(onClick = params.onClearSavedQuizzesClick) {
                    Text(
                        text = stringResource(id = StringIds.clearSavedQuizResults),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Text(
                    text = stringResource(id = StringIds.removeCompletedQuizHistoryFromThisDevice),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                )
                TextButton(onClick = params.onResetCharacterFiltersClick) {
                    Text(
                        text = stringResource(id = StringIds.resetCharacterFilters),
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Text(
                    text = stringResource(id = StringIds.clearSavedHouseDescription),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }

        item { Spacer(modifier = Modifier.height(height = 16.dp)) }

        item {
            Text(
                text = stringResource(id = StringIds.about),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            AboutCard(
                versionName = state.versionName,
                onViewDataSourceClick = params.onViewDataSourceClicked
            )
        }
    }
}

@Composable
private fun ThemeChoiceRow(
    themeOptions: List<String>,
    selectedTheme: ThemePreference,
    onThemeSelected: (ThemePreference) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(state = rememberScrollState())
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(space = 8.dp)
    ) {
        themeOptions.forEachIndexed { index, label ->
            val option = ThemePreference.entries.getOrNull(index = index) ?: return@forEachIndexed
            FilterChip(
                selected = option == selectedTheme,
                onClick = { onThemeSelected(option) },
                label = { Text(text = label) },
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = MaterialTheme.colorScheme.surface,
                    labelColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Composable
private fun ShuffleAnswersRow(checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(weight = 1f)) {
            Text(
                text = stringResource(id = StringIds.shuffleAnswerOrder),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = stringResource(id = StringIds.randomizeMultipleChoiceOrderDescription),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(
                checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                checkedTrackColor = MaterialTheme.colorScheme.primary
            )
        )
    }
}

@Composable
private fun AboutCard(
    versionName: String,
    onViewDataSourceClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = 16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(id = StringIds.potterHead),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = stringResource(StringIds.versionX, versionName),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = stringResource(id = StringIds.characterAndQuizDataDescription),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 12.dp)
            )
            TextButton(
                onClick = onViewDataSourceClick,
                modifier = Modifier.align(alignment = Alignment.Start),
                contentPadding = PaddingValues(horizontal = 0.dp)
            ) {
                Text(text = stringResource(id = StringIds.viewDataSource))
            }
        }
    }
}
