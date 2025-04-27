package com.basta.guessemoji.domain.model

import androidx.compose.ui.graphics.Color

data class GameEntry(
    val colors: List<Color>, // colors exclided / selected
    val characters: List<String>, // excluded colors emoji
    val colorsCharacters: List<String>? = null // colors emoji
)