package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.nicholas.rutherford.potter.head.compose.ui.theme.PotterHeadTheme

/**
 * Composable that displays a progress dialog with a circular progress indicator.
 * Cannot be dismissed by user interaction, suitable for blocking operations.
 *
 * @param onDismissClicked Optional callback to be invoked when the dialog is dismissed.
 *
 * @author Nicholas Rutherford
 */
@Composable
fun ProgressDialog(onDismissClicked: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(14.dp)
    ) {
        Dialog(
            onDismissRequest = { onDismissClicked?.invoke() },
            properties = DialogProperties(dismissOnBackPress = false, dismissOnClickOutside = false)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(100.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surface,
                        shape = RoundedCornerShape(12.dp)
                    )
            ) {
                Column {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

@Preview
@Composable
private fun ProgressDialogPreview() {
    PotterHeadTheme {
        ProgressDialog()
    }
}

