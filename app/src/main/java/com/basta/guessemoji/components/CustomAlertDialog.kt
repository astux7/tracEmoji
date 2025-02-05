package com.basta.guessemoji.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun CustomAlertDialog(
    title: String,
    text: String,
    buttonText: String,
    onDismissRequest: () -> Unit,
    onOkRequest: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { onDismissRequest.invoke() },
        title = {
            Text(
                text = title,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight(700)
            )
        },
        text = { Text(
            text = text,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            fontSize = 18.sp,
            fontWeight = FontWeight(700)
        ) },
        confirmButton = {
            TextButton(onClick = { onOkRequest.invoke() }) {
                Text(
                    text = buttonText,
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight(700)
                )
            }
        }
    )
}