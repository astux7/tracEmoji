package com.basta.guessemoji.presentation.game

import androidx.compose.ui.graphics.Color
import com.basta.guessemoji.domain.model.GameEntry
import com.basta.guessemoji.domain.repository.GameRepository

class GameUseCase(
    private val gameRepo : GameRepository
) {
    fun generateSingleExclusionColorGame() : GameEntry {
        val colorExcluded = gameColors().shuffled().first()
        val colorsForEmoji = gameColors().filter { it != colorExcluded }

        val emojis = colorsForEmoji.map { generateSingleExcludedColor(it) }.shuffled()
        return GameEntry(colors = listOf(colorExcluded), characters = emojis)
    }

    private fun generateSingleExcludedColor(color: Color): String = gameRepo.getSingleColorEmoji(color)

    fun gameColors() = gameRepo.getGameColors()

}