package com.basta.guessemoji.data

import androidx.compose.ui.graphics.Color
import com.basta.guessemoji.data.local.GameColor
import com.basta.guessemoji.domain.model.GameEntry
import com.basta.guessemoji.domain.repository.GameRepository

class GameRepositoryImpl: GameRepository {
    private val colors = GameColor

    override fun getGameColors(): List<Color> =
        listOf<Color>(Color.Yellow, Color.Blue, Color.Red, Color.Green)

    override fun getSingleColorEmoji(color: Color) = findEntriesWithBlueColor(color)?.characters?.shuffled()?.first() ?: ""

    private fun findEntriesWithBlueColor(color: Color): GameEntry? {
        return colors.singleColorList.findLast { gameEntry ->
            gameEntry.colors.contains(color)
        }
    }
}