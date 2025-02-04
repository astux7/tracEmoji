package com.basta.guessemoji.presentation.game.colortap


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.basta.guessemoji.domain.model.CreditType
import com.basta.guessemoji.domain.model.GameEntry
import com.basta.guessemoji.presentation.UserUseCase
import com.basta.guessemoji.presentation.game.ErrorType
import com.basta.guessemoji.presentation.game.GameUseCase
import com.basta.guessemoji.presentation.game.PageState
import com.basta.guessemoji.presentation.game.TapColorGameState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ColorTapViewModel(
    private val gameUseCase: GameUseCase,
    private val userUseCase: UserUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(TapColorGameState(pageState = PageState.Loading))
    val state: StateFlow<TapColorGameState> = _state.asStateFlow()
    private var generatedGame = mutableStateOf<GameEntry?>(null)
    private var currentGameId by mutableIntStateOf(userUseCase.getLevel())
    private var totalGuessed by mutableIntStateOf(0)
    private var totalColors by mutableIntStateOf(0)
    private var totalLives by mutableIntStateOf(3)

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
        totalGuessed = 0
        totalLives = 3
        generatedGame.value = gameUseCase.generateSliderGame()
        totalColors = generatedGame.value?.colorsCharacters?.size ?: 0
        _state.update {
            it.copy(
                pageState = PageState.Loaded,
                errorType = null,
                message = null,
                level = currentGameId + 1,
                emojis = generatedGame.value?.characters ?: emptyList(),
                totalColoredEmoji = totalColors,
                totalGuessedEmoji = totalGuessed,
                selectedColor = generatedGame.value?.colors?.first(),
                totalLives = totalLives
            )
        }
    }

    fun gameTimeOut() {
        _state.update {
            it.copy(
                pageState = PageState.End,
                level = currentGameId,
                emojis = generatedGame.value?.characters ?: emptyList(),
                totalColoredEmoji = generatedGame.value?.colorsCharacters?.size ?: 0,
                selectedColor = generatedGame.value?.colors?.first()
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

    fun submitAnswer(emoji: String) {
        if (generatedGame.value?.colorsCharacters?.contains(emoji) == true || totalGuessed == totalColors) {
            totalGuessed++
            if (totalGuessed == totalColors) {
                userUseCase.updateLevel(currentGameId)
                currentGameId++
            }
            _state.update {
                it.copy(
                    pageState = if (totalColors == totalGuessed) PageState.Success else PageState.Loaded,
                    totalGuessedEmoji = it.totalGuessedEmoji + 1,
                )
            }

        } else {
            _state.update {
                totalLives--
                it.copy(
                    errorType =if (totalLives > 0) null else ErrorType.BadAnswer,
                    pageState = if (totalLives > 0) PageState.Loaded else PageState.Error,
                    totalLives = totalLives,
                )
            }
        }
    }
}
