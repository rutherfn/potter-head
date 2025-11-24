package com.nicholas.rutherford.potter.head.feature.characters.characters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CharactersScreen(params: CharactersParams) {
    Column {
        Text(
            text = "Characters Screen",
            modifier = Modifier.padding(16.dp)
        )

        Spacer(Modifier.padding(8.dp))

        Button(
            onClick = {
                println("call it here test hsdhsd s")
                params.onCharacterClicked.invoke()
                      },
            content = {
                Text("Click Me Test")
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CharacterScreenPreview() {
    MaterialTheme {
        CharactersScreen(
            params = CharactersParams(onCharacterClicked = {})
        )
    }
}