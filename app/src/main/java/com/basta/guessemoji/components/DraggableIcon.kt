package com.basta.guessemoji.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerInputChange
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChanged
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import kotlin.math.roundToInt

@Composable
fun HoverDetectionExample() {
    var hoveredItem by remember { mutableStateOf("") }

    // Define positions for boxes (just for reference)
    val boxPositions = listOf(
        Offset(100f, 200f),
        Offset(250f, 200f),
        Offset(400f, 200f),
        Offset(550f, 200f)
    )

    // Define emoji labels
    val emojis = listOf("🌵", "❤️", "😃", "😊")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Create boxes
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            boxPositions.forEach { _ ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Blue)
                        .pointerInput(Unit) {
                            // Detect hover
                            this.awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    event.changes.forEach { change ->
                                        if (change.positionChanged()) {
                                            hoveredItem = "Box"
                                        } else {
                                            hoveredItem = ""
                                        }
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "🟦", fontSize = 60.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Create emojis below
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            emojis.forEach { emoji ->
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.LightGray)
                        .pointerInput(Unit) {
                            // Detect hover
                            this.awaitPointerEventScope {
                                while (true) {
                                    val event = awaitPointerEvent()
                                    event.changes.forEach { change ->
                                        if (change.positionChanged()) {
                                            hoveredItem = "Hovered over: $emoji"
                                        } else {
                                            hoveredItem = ""
                                        }
                                    }
                                }
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = emoji, fontSize = 60.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Display hovered item
        Text(text = hoveredItem)
    }
}

// Composable for draggable emoji
@Composable
fun DraggableEmoji(
    emoji: String,
    offset: IntOffset,
    onDrag: (change: PointerInputChange, dragAmount: Offset) -> Unit,
    onDrop: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .offset { offset }
            .pointerInput(Unit) {
                detectDragGestures(
                    onDrag = { change, dragAmount ->
                        onDrag(change, dragAmount)
                    },
                    onDragEnd = { onDrop() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(emoji, fontSize = 40.sp)
    }
}

// Function to check if an emoji is overlapping with a specific square
private fun isOverlapping(offset: IntOffset, squareIndex: Int): Boolean {
    // Define square bounds
    val squareSize = 100 // Size of the colored squares
    val squarePosition = squareIndex * (squareSize + 16) // Position based on square index

    return offset.x in (squarePosition..(squarePosition + squareSize)) &&
            offset.y in (0..(squareSize)) // Assuming all squares are at the same Y position for simplicity
}

@Preview
@Composable
fun test() {
    HoverDetectionExample()
}