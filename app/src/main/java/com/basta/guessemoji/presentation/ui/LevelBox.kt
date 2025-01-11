package com.basta.guessemoji.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.ui.theme.clockColor

@Composable
fun LevelBox(level: String) {
    Box(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .padding(top = 50.dp, end = 24.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .border(2.dp, clockColor, RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .size(52.dp, 24.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = level,
                color = MaterialTheme.colorScheme.background,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight(800),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 18.dp, start = 2.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        Box(
            Modifier
                .padding(vertical = 40.dp)
                .size(40.dp, 40.dp)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = "\uD83C\uDF1F",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(end = 2.dp, top = 2.dp)
            )
        }
    }
}