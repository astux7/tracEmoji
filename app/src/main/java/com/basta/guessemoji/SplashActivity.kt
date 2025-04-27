package com.basta.guessemoji

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT))

        // Set content with your custom splash
        setContent {
            SplashScreen()
            // Delay and go to MainActivity

            LaunchedEffect(Unit) {
                delay(2500) // 2.5 seconds delay (adjust as needed)
                startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                finish()
            }
        }
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val rotation = remember { Animatable(0f) }

        // Start rotating the whole group
        LaunchedEffect(Unit) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF0D0D25)),
            contentAlignment = Alignment.Center
        ) {
            val zoomFactor = 2.4f
            Image(
                painter = painterResource(id = R.drawable.ic_launcher),
                contentDescription = "Splash icon",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(Color.Red)
                    .graphicsLayer(
                        scaleX = zoomFactor, // Apply horizontal scaling (zoom)
                        scaleY = zoomFactor  // Apply vertical scaling (zoom)
                    )
            )

            Box(
                modifier = Modifier
                    .size(300.dp)
                    .graphicsLayer {
                        rotationZ = rotation.value
                    },
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EmojiText("\uD83D\uDFE1") // yellow
                        Spacer(modifier = Modifier.width(60.dp))
                        EmojiText("\uD83D\uDD35") // blue
                    }
                    Spacer(modifier = Modifier.height(60.dp))
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EmojiText("\uD83D\uDFE2") // green
                        Spacer(modifier = Modifier.width(60.dp))
                        EmojiText("\uD83D\uDD34") // red
                    }
                }
            }
        }
    }
}

@Composable
fun EmojiText(emoji: String) {
    // Create a state to hold the random font size
    var fontSize by remember { mutableFloatStateOf(80f) }

    // Trigger a random change in font size every second
    LaunchedEffect(Unit) {
        while (true) {
            // Randomly set font size to one of 50.sp, 60.sp, 70.sp, or 80.sp
            fontSize = listOf(65f, 70f, 75f, 80f, 70f, 60f, 65f, 75f).random()
            delay(400) // Wait for 1 second before changing the font size
        }
    }

    Text(
        text = emoji,
        style = TextStyle(fontSize = fontSize.sp),
        modifier = Modifier
            .size(100.dp) // Fixed size for the container
            .wrapContentSize(Alignment.Center) // Center the emoji inside the fixed-size container
    )
}
