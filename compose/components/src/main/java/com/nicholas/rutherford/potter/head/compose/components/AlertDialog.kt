package com.nicholas.rutherford.potter.head.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.util.Locale

/**
 * Default [AlertDialog] with given params to build alerts used in content.
 *
 * @param title Sets the [Text] for the [AlertDialog].
 * @param onDismissClicked Optional callback when the user dismisses the dialog (e.g. outside tap).
 * @param confirmButton Optional pair of (button label, onClick). First = label, second = action.
 * @param dismissButton Optional pair of (button label, onClick). First = label, second = action.
 * @param description Optional text shown in the body of the dialog.
 */
@Composable
fun AlertDialog(
    title: String,
    onDismissClicked: (() -> Unit)? = null,
    confirmButton: Pair<String, () -> Unit>? = null,
    dismissButton: Pair<String, () -> Unit>? = null,
    description: String? = null
) {
    AlertDialog(
        onDismissRequest = { onDismissClicked?.invoke() },
        confirmButton = {
            confirmButton?.let { (label, onClick) ->
                TextButton(
                    onClick = onClick,
                    content = { Text(text = label.uppercase(Locale.ROOT)) },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                )
            }
        },
        dismissButton = {
            dismissButton?.let { (label, onClick) ->
                TextButton(
                    onClick = onClick,
                    content = { Text(text = label.uppercase(Locale.ROOT)) },
                    colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.secondary)
                )
            }
        },
        title = { Text(text = title) },
        text = { description?.let { text -> Text(text = text) } },
        modifier = Modifier.padding(32.dp),
        shape = RoundedCornerShape(25.dp)
    )
}

@Composable
@Preview
fun AlertDialogPreview() {
    AlertDialog(
        title = "Title 1",
        onDismissClicked = {},
        confirmButton = "Confirm" to {},
        dismissButton = "Dismiss" to {},
        description = "Here is a set description for the alert"
    )
}