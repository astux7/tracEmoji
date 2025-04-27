package com.basta.guessemoji.presentation.game

import androidx.compose.ui.graphics.Color
import com.basta.guessemoji.domain.model.GameEntry
import com.basta.guessemoji.domain.repository.GameRepository

class GameUseCase(
    private val gameRepo : GameRepository
) {
    companion object {
        const val TOTAL_SLIDER_ITEMS = 20
        const val MIN_START = 4
        const val MAX_END = 6
    }
    fun generateSingleExclusionColorGame() : GameEntry { // pick a color
        val colorExcluded = gameColors().shuffled().first()
        val colorsForEmoji = gameColors().filter { it != colorExcluded }

        val emojis = colorsForEmoji.map { generateSingleExcludedColor(it) }.shuffled()
        return GameEntry(colors = listOf(colorExcluded), characters = emojis)
    }

    private fun generateSingleExcludedColor(color: Color): String = gameRepo.getSingleColorEmoji(color)
    private fun generateSingleColorEmojis(color: Color) = gameRepo.getSingleColorEmojis(color) ?: emptyList()

    private fun gameColors() = gameRepo.getGameColors()

    fun generateSliderGame() : GameEntry {
        val randomColorCount = (MIN_START..MAX_END).random()

        val colorSelected = gameColors().shuffled().first()
        val excludedColors = gameColors().filter { it != colorSelected }

        val colorsCharacters: List<String> = generateSingleColorEmojis(colorSelected).take(randomColorCount)
        val restOfEmojis = excludedColors.map { generateSingleColorEmojis(it) }.flatten().take(TOTAL_SLIDER_ITEMS - randomColorCount)

        return GameEntry(colors = listOf(colorSelected), characters = (restOfEmojis + colorsCharacters).shuffled(), colorsCharacters = colorsCharacters)
    }
}