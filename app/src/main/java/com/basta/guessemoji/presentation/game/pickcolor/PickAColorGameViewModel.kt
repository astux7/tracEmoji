package com.basta.guessemoji.presentation.game.pickcolor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.basta.guessemoji.domain.model.CreditType
import com.basta.guessemoji.domain.model.GameEntry
import com.basta.guessemoji.presentation.UserUseCase
import com.basta.guessemoji.presentation.game.ErrorType
import com.basta.guessemoji.presentation.game.GameUseCase
import com.basta.guessemoji.presentation.game.PageState
import com.basta.guessemoji.presentation.game.PickAColorGameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PickAColorGameViewModel(
    private val gameUseCase: GameUseCase,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(PickAColorGameState(pageState = PageState.Loading))
    val state: StateFlow<PickAColorGameState> = _state.asStateFlow()
    private var generatedGame = mutableStateOf<GameEntry?>(null)
    private var currentGameId by mutableIntStateOf(userUseCase.getLevel())
    private var currentLives by mutableIntStateOf(userUseCase.getLives())

    fun startGame() {
        _state.update {
            it.copy(
                pageState = PageState.Start,
                errorType = null,
                message = null,
                level = currentGameId + 1,
                lives = currentLives
            )
        }
    }

    private fun noLivesGame() {
        _state.update {
            it.copy(
                pageState = PageState.Start,
                errorType = null,
                message = null,
                level = currentGameId,
                lives = currentLives
            )
        }
    }

    fun loadGame() {
        if (currentLives > 0) {
            generatedGame.value = gameUseCase.generateSingleExclusionColorGame()
            _state.update {
                it.copy(
                    pageState = PageState.Loaded,
                    errorType = null,
                    message = null,
                    level = currentGameId + 1,
                    lives = currentLives,
                    emojis = generatedGame.value?.characters ?: emptyList()
                )
            }
        } else noLivesGame()
    }

    fun gameTimeOut() {
        userUseCase.removeLive(1)
        currentLives--
        _state.update {
            it.copy(
                pageState = PageState.End,
                errorType = null,
                message = null,
                level = currentGameId,
                lives = currentLives,
                emojis = generatedGame.value?.characters ?: emptyList(),
            )
        }
    }

    fun updateCredits(type: CreditType) {
        val credit = when(type) {
            CreditType.ADS -> CreditType.ADS.credit
            CreditType.FAIL -> CreditType.FAIL.credit
            CreditType.TIMEOUT -> CreditType.TIMEOUT.credit
            CreditType.UNLOCK -> CreditType.UNLOCK.credit
            CreditType.WELCOME -> CreditType.WELCOME.credit
        }
        userUseCase.updateCredits(credit)
    }

    fun submitAnswer(color: Color) {
        if (color == generatedGame.value?.colors?.first()) {
            currentGameId++
            userUseCase.updateLevel(currentGameId)
            _state.update {
                it.copy(
                    pageState = PageState.Success,
                    errorType = null,
                    message = null,
                    level = currentGameId,
                    emojis = generatedGame.value?.characters ?: emptyList()
                )
            }

        } else {
            userUseCase.removeLive(1)
            currentLives--
            _state.update {
                it.copy(
                    pageState = PageState.Error,
                    errorType = ErrorType.BadAnswer,
                    message = null,
                    level = currentGameId,
                    lives = currentLives,
                    emojis = generatedGame.value?.characters ?: emptyList()
                )
            }
        }
    }
}
