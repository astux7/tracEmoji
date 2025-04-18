package com.basta.guessemoji.presentation.home

import androidx.lifecycle.ViewModel
import com.basta.guessemoji.presentation.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(UserState())
    val state: StateFlow<UserState> = _state.asStateFlow()

    fun setUp() {
        checkForUpdates()
    }

    private fun checkForUpdates() {
        userUseCase.checkForUpdates()
        _state.update {
            it.copy(
                lives = getUserLives(),
                coins = getUserCredits(),
                level = getUserLevel(),
                boughtTapGame = hasBoughtTapGame()
            )
        }
    }

    private fun getUserLevel() = userUseCase.getLevel()

    private fun getUserCredits() = userUseCase.getCredits()

    private fun getUserLives() = userUseCase.getLives()

    private fun hasBoughtTapGame() = userUseCase.hasBoughtTapGame()
}