package com.basta.guessemoji.presentation.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.basta.guessemoji.data.local.GameColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel(
) : ViewModel() {

    private val _state = MutableStateFlow(GameState(pageState = PageState.Loading))
    val state: StateFlow<GameState> = _state.asStateFlow()
    private val data = GameColor.list1 // fix later
    private val dataNow= data[0].characters.shuffled().take(4)
    private var currentGameId by mutableIntStateOf(0)

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
        _state.update {
            it.copy(
                pageState = PageState.Loaded,
                level = currentGameId + 1,
                emojis = dataNow
            )
        }
    }

    fun gameTimeOut() {
        _state.update {
            it.copy(
                pageState = PageState.Error,
                level = currentGameId + 1,
                emojis = dataNow,
            )
        }
    }

    fun submitAnswer(color: Color) {
        if (color in data[currentGameId].colors) {
            currentGameId++
            _state.update {
                it.copy(
                    pageState = PageState.Success,
                    errorType = null,
                    message = null,
                    level = currentGameId,
                    emojis = dataNow
                )
            }

        }else {
            _state.update {
                it.copy(
                    pageState = PageState.Error,
                    errorType = ErrorType.BadAnswer,
                    message = null,
                    level = currentGameId,
                    emojis = data[currentGameId].characters
                )
            }
        }
    }
}