package com.basta.guessemoji.presentation.home

data class UserState(
    val level: Int = 0,
    val coins: Int = 0,
    val lives: Int = 0,
    val boughtTapGame: Boolean = false,
)
