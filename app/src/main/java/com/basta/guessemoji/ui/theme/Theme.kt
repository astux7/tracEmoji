package com.basta.guessemoji.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//val GameColorPalette = listOf(
//    Color(0xFF1D1F21), // Deep Charcoal - Background
//    Color(0xFF4A90E2), // Sky Blue - Primary color
//    Color(0xFFE94E77), // Bright Pink - Secondary color
//    Color(0xFFFABF2D), // Bright Yellow - Accent color
//    Color(0xFFB5E1FA), // Light Sky Blue - Surface color
//    Color(0xFF1ED760), // Neon Green - Highlight color
//    Color(0xFFDA44FF), // Bright Purple - Alternative accent color
//    Color(0xFFFFB300), // Gold - Border or detail color
//    Color(0xFF28B4A0), // Teal - Action elements
//    Color(0xFF7B1FA2), // Deep Purple - Text color
//    Color(0xFF616161), // Medium Grey - Disabled or secondary text
//    Color(0xFFE57373)  // Light Red - Error messages or warnings
//)

val GameColorPalette = listOf(
    Color(0xFF0D0D25), // Deep Space Blue
    Color(0xFF1A1A4B), // Nebula Purple
    Color(0xFF4B0082), // Indigo
    Color(0xFF800080), // Cosmic Purple
    Color(0xFF00FF99), // Neon Green
    Color(0xFFFF007F), // Magenta
    Color(0xFFFFD700), // Star Gold
    Color(0xFF00BFFF), // Deep Sky Blue
    Color(0xFFB22222), // Firebrick Red
    Color(0xFFFFFFF0), // Moonlight White
    Color(0xFFFF5722), // Moonlight White
)

private val DarkColorScheme = darkColorScheme(
    background = GameColorPalette[0], // Deep Charcoal
    surface = GameColorPalette[4], // Light Sky Blue
    onSurface = GameColorPalette[7], // Deep Purple for text
    onBackground = Color.White // White text on deep background
//    primary = Color(0xFF1DA1F2),  // Sky Blue
//    onPrimary = Color.White,
//    secondary = Color(0xFF536DFE),  // Indigo
//    onSecondary = Color.White,
//    background = Color(0xFF0D0D0D),  // Deep Dark Background
//    onBackground = Color.White,
//    surface = Color(0xFF1E1E1E),  // Dark Surface
//    onSurface = Color.White,
//    error = Color(0xFFFF5252),  // Bright Red
//    onError = Color.White
)
val borderColor = GameColorPalette[1] // Purple for the border
val clockColor = GameColorPalette.last() // Purple for the border

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}