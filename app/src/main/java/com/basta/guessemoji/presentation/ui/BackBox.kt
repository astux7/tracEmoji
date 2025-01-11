package com.basta.guessemoji.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.navigation.NavController
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.ui.theme.MenuColor

@Composable
fun BackButton(navController: NavController) {
    Box(
        Modifier
            .padding(vertical = 40.dp)
            .offset(x = (-3).dp)
            .size(40.dp)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .border(2.dp, Color.White, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(MenuColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "↩",
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(700),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clickable {
                    navController.navigate(Directions.home.name)
                }
        )
    }
}