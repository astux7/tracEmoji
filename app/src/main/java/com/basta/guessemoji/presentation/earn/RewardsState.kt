package com.basta.guessemoji.presentation.earn

data class RewardsState(
    val isAdReady: AdStatus = AdStatus.LOADING,
    val totalEarned: Int = 0,
    val lives: Int = 0,
    val hasBoughtTapGame: Boolean = false,
)
