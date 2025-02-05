package com.basta.guessemoji.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.ui.theme.clockColor

@Composable
fun LivesBox(lives: Int, event: () -> Unit) {
    Box(
        Modifier
            .fillMaxWidth()
            .offset(x = 6.dp)
    ) {
        Box(
            Modifier
                .padding(top = 50.dp, end = 4.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .border(2.dp, clockColor, RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .size(62.dp, 24.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row {
                Box(
                    Modifier
                        .padding(top = 3.dp, start = 3.dp)
                        .size(18.dp)
                        .clickable {
                            event.invoke()
                        }
                        .background(
                            color = Color(0xFF006400),
                            shape = CircleShape
                        ), // Dark Green Rounded Background
                    contentAlignment = Alignment.CenterEnd,
                ) {

                    Text(
                        text = "➕",
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold,
                        color = Color.Transparent, // Make text visible
                        style = TextStyle(shadow = Shadow(color = Color.White)),
                        modifier = Modifier.padding(horizontal = 2.dp, vertical = 2.dp)
                    )

                }
                val heartIcon = if (lives == 0) "💔" else "❤️"
                Text(
                    text = "$lives $heartIcon",
                    color = MaterialTheme.colorScheme.background,
                    fontSize = 14.sp,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight(800),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 6.dp, start = 2.dp)
                )

            }
        }
    }
}
