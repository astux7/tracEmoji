package com.basta.guessemoji.presentation.play

import androidx.lifecycle.ViewModel
import com.basta.guessemoji.presentation.UserUseCase

class HomeViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {
    fun getUserLevel() = userUseCase.getLevel()

    fun getUserCredits() = userUseCase.getCredits()
}