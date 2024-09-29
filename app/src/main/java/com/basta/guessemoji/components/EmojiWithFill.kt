package com.basta.guessemoji.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun EmojiWithFill(text: String, color: Color? = null, size: Int = 40) {
    val showEmoji = color?.let {
        TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Transparent,
            shadow = Shadow(color = color)
        )
    } ?: TextStyle(
        fontSize = size.sp,
        fontWeight = FontWeight.Bold,
    )
    Text(
        text = text,
        style = showEmoji
    )
}

@Preview
@Composable
private fun EmojiWithFillPreview() {
    Column {
        EmojiWithFill("\uD83D\uDC36 \uD83C\uDF32 \uD83C\uDF49 \uD83C\uDFA8")
        EmojiWithFill("\uD83D\uDC36 \uD83C\uDF32 \uD83C\uDF49 \uD83C\uDFA8", Color.Blue)
    }
}