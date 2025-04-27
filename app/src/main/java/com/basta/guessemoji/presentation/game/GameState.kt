package com.basta.guessemoji.presentation.game

import androidx.compose.ui.graphics.Color

data class PickAColorGameState(
    val pageState: PageState = PageState.Loading,
    val errorType: ErrorType? = null,
    val message: String? = null,
    val level: Int? = null,
    val lives: Int? = null,
    val credits: Int? = null,
    val emojis: List<String> = emptyList(),
)

data class TapColorGameState(
    val pageState: PageState = PageState.Loading,
    val errorType: ErrorType? = null,
    val message: String? = null,
    val level: Int? = null,
    val lives: Int? = null,
    val credits: Int? = null,
    val emojis: List<String> = emptyList(),
    val totalColoredEmoji: Int = 0,
    val totalGuessedEmoji: Int = 0,
    val selectedColor: Color? = null,
)

enum class PageState {
    Loading,
    Loaded,
    Error,
    Success,
    Start,
    End,
}

enum class ErrorType {
    Generic,
    Network,
    BadAnswer,
}
