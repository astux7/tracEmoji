package com.basta.guessemoji.domain.model

import androidx.compose.ui.graphics.Color

data class GameEntry(
    val colors: List<Color>,
    val characters: List<String>
)