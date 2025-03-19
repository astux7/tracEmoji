package com.basta.guessemoji.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.common.EmojiConstants.BROKEN_HEART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.HEART_EMOJI
import com.basta.guessemoji.common.utils.toEmoji

@Composable
fun LivesAndAmountRow(
    lives: Int, // Current number of lives
    found: Int, // emoji found count
    amount: Int, // emoji total count
    maxLives: Int = 3, // Total number of hearts (lives)
    selectedColor: Color // Color for the center highlight box
) {
    Column(Modifier.padding(bottom = 32.dp)) {
        HorizontalDivider(thickness = 2.dp, color = Color.White)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {


            // Display the lives with hearts
            Row(modifier = Modifier.padding(4.dp)) {
                repeat(maxLives) { index ->
                    val isHeartBroken =
                        index >= lives // If heart index is greater or equal to lives, it's broken
                    val heartIcon =
                        if (isHeartBroken) BROKEN_HEART_EMOJI else HEART_EMOJI // Use broken heart if it's lost

                    Text(
                        text = heartIcon,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }

            // Add space between hearts and selected color box
            Spacer(modifier = Modifier.weight(1f))

            // Display the selected color box in the middle with a border and shadow
            Row(modifier = Modifier.padding(4.dp)) {
                Text(
                    text = "Color: " + selectedColor.toEmoji(),
                    color = Color.White,
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { },
                    fontSize = 15.sp
                )
            }

            // Add space between the color box and amount
            Spacer(modifier = Modifier.weight(1f))

            // Display the amount with the books emoji
            Text(
                text = "\uD83D\uDD0E $found / $amount",
                fontSize = 15.sp,
                modifier = Modifier.padding(4.dp)
            )

        }
        HorizontalDivider(thickness = 2.dp, color = Color.White)
    }
}

@Preview
@Composable
fun PreviewLivesAndAmountRowWithBlueColor() {
    LivesAndAmountRow(
        lives = 2,
        amount = 7,
        found = 2,
        selectedColor = Color.Blue
    ) // Blue selected color box
}

@Preview
@Composable
fun PreviewLivesAndAmountRowWithYellowColor() {
    LivesAndAmountRow(
        lives = 1,
        amount = 7,
        found = 1,
        selectedColor = Color.Yellow
    ) // Yellow selected color box
}

@Preview
@Composable
fun PreviewLivesAndAmountRowWithRedColor() {
    LivesAndAmountRow(
        lives = 3,
        amount = 7,
        found = 0,
        selectedColor = Color.Red
    ) // Red selected color box
}
