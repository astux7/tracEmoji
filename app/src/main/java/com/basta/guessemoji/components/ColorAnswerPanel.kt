package com.basta.guessemoji.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ColorAnswerPanel(action: (Color) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "\uD83D\uDFE8",
            modifier = Modifier
                .padding(4.dp)
                .clickable { action.invoke(Color.Yellow) },
            fontSize = 80.sp
        )
        Text(
            text = "\uD83D\uDFE6",
            modifier = Modifier
                .padding(4.dp)
                .clickable { action.invoke(Color.Blue) },
            fontSize = 80.sp
        )
    }
    Row(
        Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = "\uD83D\uDFE5",
            modifier = Modifier
                .padding(4.dp)
                .clickable { action.invoke(Color.Red) },
            fontSize = 80.sp
        )
        Text(
            text = "\uD83D\uDFE9",
            modifier = Modifier
                .padding(4.dp)
                .clickable { action.invoke(Color.Green) },
            fontSize = 80.sp
        )
    }
}

@Preview
@Composable
private fun ColorPanelPreview() {
    ColorAnswerPanel {
        Color.Blue
    }
}