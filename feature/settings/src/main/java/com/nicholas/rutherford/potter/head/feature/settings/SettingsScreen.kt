package com.nicholas.rutherford.potter.head.feature.settings

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(params: SettingsParams) {
    Text(
        text = "Settings Screen",
        modifier = Modifier.padding(16.dp)
    )
}
