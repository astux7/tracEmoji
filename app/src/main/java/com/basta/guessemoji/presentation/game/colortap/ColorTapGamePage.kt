package com.basta.guessemoji.presentation.game.colortap

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.common.Constants.TAP_COLOR_TIMER
import com.basta.guessemoji.components.EmojiSlideShow
import com.basta.guessemoji.components.LivesAndAmountRow
import com.basta.guessemoji.components.LottieAnimationLoader
import com.basta.guessemoji.presentation.game.ErrorType
import com.basta.guessemoji.presentation.game.PageState
import com.basta.guessemoji.presentation.game.ui.CongratsBox
import com.basta.guessemoji.presentation.game.ui.FailBox
import com.basta.guessemoji.presentation.ui.BackButton
import com.basta.guessemoji.presentation.ui.LevelBox
import com.basta.guessemoji.presentation.ui.TimerBox
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun ColorTapGamePage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: ColorTapViewModel = getViewModel()
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.startGame() }

    var isVisibleTimer by remember { mutableStateOf(false) }
    var timeCalculator by remember { mutableIntStateOf(TAP_COLOR_TIMER) }
    var startTimer by remember { mutableStateOf(false) }
    var selectedColor by remember { mutableStateOf<Color?>(null) }

    LaunchedEffect(startTimer) {
        if (startTimer) {
            // Run the timer until 10 seconds is reached
            while (timeCalculator > 0) {
                delay(1000) // Delay for 1 second
                timeCalculator -= 1
            }
            startTimer = false
            viewModel.gameTimeOut()
        }
    }

    Box(Modifier.fillMaxSize()) {
        BackButton(navController)

        LevelBox(state.value.level.toString())

        Column(
            Modifier
                .fillMaxSize()
                .padding(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Rtl)
                ),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TimerBox(isVisibleTimer, timeCalculator)

            Spacer(modifier = Modifier.padding(top = 16.dp))
            when (state.value.pageState) {
                PageState.Loading -> LottieAnimationLoader(rawRes = R.raw.loading)
                PageState.Loaded -> {
                    startTimer = true
                    LaunchedEffect(Unit) {
                        delay(400)
                        isVisibleTimer = true
                    }

                    state.value.selectedColor?.let {
                        LivesAndAmountRow(
                            lives = state.value.totalLives,
                            amount = state.value.totalColoredEmoji,
                            found = state.value.totalGuessedEmoji,
                            selectedColor = it
                        )
                    }

                    EmojiSlideShow(state.value.emojis, onAction = { emoji -> viewModel.submitAnswer(emoji)})

                }

                PageState.Error -> {
                    timeCalculator = TAP_COLOR_TIMER
                    selectedColor = null
                    state.value.errorType?.let { error ->
                        when (error) {
                            ErrorType.Generic -> LottieAnimationLoader(rawRes = R.raw.generic_error)

                            ErrorType.Network -> LottieAnimationLoader(rawRes = R.raw.no_connection)

                            ErrorType.BadAnswer -> {
                                FailBox(
                                    title = stringResource(id = R.string.game_failed),
                                    color = null,
                                    emojis = "\uD83D\uDC94 \uD83D\uDC94 \uD83D\uDC94 \n",
                                    nextAction = {
                                        timeCalculator = TAP_COLOR_TIMER
                                        selectedColor = null
                                        viewModel.loadGame()
                                    }
                                )
                            }
                        }
                    }
                }

                PageState.Start -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.7f)
                            .border(2.dp, Color.White, RoundedCornerShape(16.dp))
                            .clip(RoundedCornerShape(16.dp))
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        timeCalculator = TAP_COLOR_TIMER
                        selectedColor = null
                        Text(
                            text = stringResource(id = R.string.game_color_description_title),
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.game_tap_color_description),
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                viewModel.loadGame()
                            }
                        ) {
                            Text(text = stringResource(id = R.string.go_label))
                        }
                    }
                }

                PageState.End -> {
                    LaunchedEffect(Unit) {
                        delay(400)
                        isVisibleTimer = false
                    }
                    selectedColor = null
                    timeCalculator = TAP_COLOR_TIMER

                    FailBox(
                        title = stringResource(id = R.string.game_timeout),
                        color = selectedColor,
                        emojis = "⏰",
                        nextAction = {
                            viewModel.loadGame()
                        }
                    )
                }

                PageState.Success -> {
                    LaunchedEffect(Unit) {
                        delay(400)
                        isVisibleTimer = false
                    }
                    startTimer = false
                    timeCalculator = TAP_COLOR_TIMER
                    selectedColor = null

                    CongratsBox(
                        correctColor = null,
                        emojis = "You won!",
                        nextAction = {
                            viewModel.loadGame()
                        }
                    )
                }
            }
        }
    }
}
