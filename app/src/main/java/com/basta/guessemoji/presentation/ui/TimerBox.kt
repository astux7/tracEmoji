package com.basta.guessemoji.presentation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.basta.guessemoji.R
import com.basta.guessemoji.ui.theme.clockColor

@Composable
fun TimerBox(isVisibleTimer: Boolean, timeCalculator: Int) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .height(80.dp)
                .width(1.dp)
        ) // keep height

        AnimatedVisibility(
            visible = isVisibleTimer,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.5f)
                    .offset(y = (-3).dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .border(
                        2.dp,
                        Color.White,
                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .background(clockColor)
                    .padding(top = 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Center the text horizontally at the top
                Text(
                    text = stringResource(id = R.string.game_time_calculator, timeCalculator),
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopCenter) // Align at the top center of the screen
                )
            }
        }
    }
}