package com.basta.guessemoji.presentation.profile

import androidx.lifecycle.ViewModel
import com.basta.guessemoji.presentation.UserUseCase

class ProfileViewModel(private val useCase: UserUseCase): ViewModel() {
    fun getUserLevel() = useCase.getLevel()

    fun getUserCredits() = useCase.getCredits()
}