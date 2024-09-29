package com.basta.guessemoji.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.basta.guessemoji.ui.theme.borderColor

@Composable
fun TextBoxWithIcon(modifier: Modifier, text: String, emoji: String, action: (() -> Unit)? = null) {
    Box(
        modifier = modifier
            .padding(16.dp)
            .aspectRatio(1f)
            .clickable { action?.invoke() }
            .border(
                BorderStroke(2.dp, borderColor),
                shape = RoundedCornerShape(16.dp)
            ) // 2px border
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            EmojiWithFill(emoji)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = text,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight(700)
            )
        }
    }
}

@Preview
@Composable
private fun TextBoxWithIconPreview() {
    TextBoxWithIcon(Modifier.fillMaxWidth(),"Settings", "⚙\uFE0F")
}
