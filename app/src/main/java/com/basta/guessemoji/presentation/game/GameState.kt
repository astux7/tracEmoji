package com.basta.guessemoji.presentation.game

data class GameState(
    val pageState: PageState = PageState.Loading,
    val errorType: ErrorType? = null,
    val message: String? = null,
    val level: Int? = null,
    val emojis: List<String> = emptyList(),
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
