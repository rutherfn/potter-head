package com.nicholas.rutherford.potter.head.feature.quizzes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.nicholas.rutherford.potter.head.core.StringIds

@Composable
fun QuizzesScreen(params: QuizzesParams) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        QuizFilterChips(state = params.state, onFilterItemClicked = params.onFilterItemClicked)
        
        Text(
            text = "Quizzes Screen",
            modifier = Modifier.padding(all = 16.dp)
        )
    }
}

@Composable
private fun QuizFilterChips(state: QuizzesState, onFilterItemClicked: (index: Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        state.filterTypes.forEachIndexed { index, filter ->
            val isSelected = index == state.selectedFilterIndex
            
            FilterChip(
                selected = isSelected,
                onClick = { onFilterItemClicked(index) },
                label = { Text(text = filter) },
                modifier = Modifier.padding(end = if (index < state.filterTypes.size - 1) 12.dp else 0.dp),
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