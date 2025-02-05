package com.basta.guessemoji.presentation.play

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
        _state.update {
            it.copy(
                lives = 0,
                coins = getUserCredits(),
                level = getUserLevel()
            )
        }
    }

    private fun getUserLevel() = userUseCase.getLevel()

    private fun getUserCredits() = userUseCase.getCredits()
}