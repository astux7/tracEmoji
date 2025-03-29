package com.basta.guessemoji.presentation.game.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.R
import com.basta.guessemoji.components.CustomButton
import com.basta.guessemoji.ui.theme.ButtonBorder
import com.basta.guessemoji.ui.theme.borderColor

@Composable
fun InfoBox(title: String, text: String, buttonLabel: Int, action: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.7f)
            .border(1.dp, ButtonBorder, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF00008B), Color(0xFF87CEFA)) // Dark Blue to Light Blue
                )
            )
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(bottom = 8.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.W700,
            textAlign = TextAlign.Center
        )
        Text(
            text = text,
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            onClick = {  action.invoke() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray, contentColor = Color.Black),
            border = BorderStroke(1.dp, Color.Gray),
        ) {
            Text(text = stringResource(id = buttonLabel))
        }
    }
}