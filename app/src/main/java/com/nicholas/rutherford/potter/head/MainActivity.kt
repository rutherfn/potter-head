package com.nicholas.rutherford.potter.head

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PotterHeadTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar(windowInsets = NavigationBarDefaults.windowInsets) {
                            NavigationBarItem(
                                selected = false,
                                onClick = {},
                                icon = {
                                    Icon(
                                        Icons.Default.Group,
                                        contentDescription = ""
                                    )
                                },
                                label = { Text("Characters") }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = {},
                                icon = {
                                    Icon(
                                        Icons.Default.Quiz,
                                        contentDescription = ""
                                    )
                                },
                                label = { Text("Quizzes") }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = {},
                                icon = {
                                    Icon(
                                        Icons.Default.Settings,
                                        contentDescription = ""
                                    )
                                },
                                label = { Text("Settings") }
                            )
                        }
                    }
                ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PotterHeadTheme {
        Greeting("Android")
    }
}
