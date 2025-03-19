package com.basta.guessemoji.presentation.game.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.R
import com.basta.guessemoji.common.utils.toEmoji
import com.basta.guessemoji.components.EmojiWithFill

@Composable
fun FailBox(title: String, color: Color?, emojis: String, nextAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(2.dp, Color.White, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        EmojiWithFill(emojis)

        color?.let {
            Text(
                text = stringResource(id = R.string.game_wrong_answer, color.toEmoji()),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            onClick = nextAction
        ) {
            Text(text = stringResource(id = R.string.next_label))
        }
    }
}

@Preview
@Composable
private fun FailBoxPreview() {
    Surface(Modifier.fillMaxWidth(), color = Color.Blue) {
        FailBox(title = "Title", color = null, emojis = "") {}
    }
}