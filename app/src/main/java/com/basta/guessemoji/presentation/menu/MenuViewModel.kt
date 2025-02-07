package com.basta.guessemoji.presentation.menu

import androidx.lifecycle.ViewModel
import com.basta.guessemoji.presentation.UserUseCase

class MenuViewModel(private val useCase: UserUseCase) : ViewModel() {
    val appVersion = "1.0.0"

    fun getUserLevel() = useCase.getLevel()

    fun getUserCredits() = useCase.getCredits()

    fun getUserLives() = useCase.getLives()

    fun getProfileIcon() = when (useCase.getLevel()) {
        0 -> "\uD83E\uDEB9"
        in 1..25 -> "\uD83E\uDEBA"
        in 26..50 -> "\uD83D\uDC23"
        in 51..75 -> "\uD83D\uDC25"
        in 76..100 -> "\uD83D\uDC25"
        else -> "\uD83E\uDD89"
    }
}
