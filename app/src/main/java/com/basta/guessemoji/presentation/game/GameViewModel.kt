package com.basta.guessemoji.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.basta.guessemoji.domain.model.CreditType
import com.basta.guessemoji.domain.model.GameEntry
import com.basta.guessemoji.presentation.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(
    private val gameUseCase: GameUseCase,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(GameState(pageState = PageState.Loading))
    val state: StateFlow<GameState> = _state.asStateFlow()
    private var generatedGame = mutableStateOf<GameEntry?>(null)
    private var currentGameId by mutableIntStateOf(userUseCase.getLevel())

    fun startGame() {
        _state.update {
            it.copy(
                pageState = PageState.Start,
                errorType = null,
                message = null,
                level = currentGameId + 1
            )
        }
    }

    fun loadGame() {
        generatedGame.value = gameUseCase.generateSingleExclusionColorGame()
        _state.update {
            it.copy(
                pageState = PageState.Loaded,
                errorType = null,
                message = null,
                level = currentGameId + 1,
                emojis = generatedGame.value?.characters ?: emptyList()
            )
        }
    }

    fun gameTimeOut() {
        _state.update {
            it.copy(
                pageState = PageState.End,
                errorType = null,
                message = null,
                level = currentGameId,
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
            userUseCase.updateLevel(currentGameId)
            currentGameId++
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
            _state.update {
                it.copy(
                    pageState = PageState.Error,
                    errorType = ErrorType.BadAnswer,
                    message = null,
                    level = currentGameId,
                    emojis = generatedGame.value?.characters ?: emptyList()
                )
            }
        }
    }
}
