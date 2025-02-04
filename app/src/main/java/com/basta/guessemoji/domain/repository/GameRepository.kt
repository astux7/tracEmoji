package com.basta.guessemoji.domain.repository

import androidx.compose.ui.graphics.Color

interface GameRepository {
    fun getGameColors(): List<Color>
    fun getSingleColorEmoji(color: Color): String
    fun getSingleColorEmojis(color: Color): List<String>?
}