package com.basta.guessemoji.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EmojiSlideShow(
    emojis: List<String>,
    durationMillis: Int = 1000, // Duration for the movement from right to center or center to left
    pauseMillis: Int = 0, // Pause in the center before moving left
    onAction: (String) -> Unit
) {
    var currentIndex by remember { mutableIntStateOf(0) }

    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val leftOffset = -screenWidth.value / 2 - 40
    val centerOffset = 0f
    val rightOffset = screenWidth.value / 2 + 40

    // Animatable offset for the current emoji
    val currentOffsetX = remember { Animatable(rightOffset) }
    val selectedEmoji = remember { mutableStateOf(false) }

    LaunchedEffect(currentIndex) {
        // Animate the current emoji from right to center
        launch {
            currentOffsetX.animateTo(centerOffset, animationSpec = tween(durationMillis / 2))
        }

        // Step 2: Pause at the center for the specified time
        delay(durationMillis.toLong())

        // Step 3: Animate the current emoji from center to left
        launch {
            currentOffsetX.animateTo(leftOffset, animationSpec = tween(durationMillis / 2))
        }

        // Step 4: After the first emoji completes its cycle, update the index to show the next emoji
        delay(durationMillis.toLong() / 2) // Wait for current emoji to move out
        selectedEmoji.value = false

        // Reset the position for the next cycle
        currentOffsetX.snapTo(rightOffset)
        currentIndex += 1
    }

    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        // Display the current emoji (starts from right, moves to center, then left)
        EmojiWithFill(
            text = emojis[currentIndex],
            color = if (selectedEmoji.value) null else Color.White,
            size = 70,
            modifier = Modifier
                .offset(x = currentOffsetX.value.dp)
                .pointerInput(Unit) {
                    detectTapGestures {
                        selectedEmoji.value = true
                        onAction.invoke(emojis[currentIndex])
                    }
                },
        )
    }
}


@Preview
@Composable
fun PreviewEmojiSlideShow() {
    val emojis = listOf("🎨", "🤚", "👍", "😊", "🎮")
    EmojiSlideShow(emojis, onAction = { _ -> })
}

