package com.basta.guessemoji.presentation.game.colortap

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.common.Constants.TAP_COLOR_TIMER
import com.basta.guessemoji.components.EmojiSlideShow
import com.basta.guessemoji.components.LivesAndAmountRow
import com.basta.guessemoji.components.LottieAnimationLoader
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.presentation.game.ErrorType
import com.basta.guessemoji.presentation.game.PageState
import com.basta.guessemoji.presentation.game.ui.CongratsBox
import com.basta.guessemoji.presentation.game.ui.FailBox
import com.basta.guessemoji.presentation.game.ui.InfoBox
import com.basta.guessemoji.presentation.ui.BackButton
import com.basta.guessemoji.presentation.ui.LevelBox
import com.basta.guessemoji.presentation.ui.LivesBox
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
            // Run the timer until 30 seconds is reached
            while (timeCalculator > 0) {
                delay(1000) // Delay for 1 second
                timeCalculator -= 1
            }
            startTimer = false
            viewModel.gameTimeOut()
        }
    }

    Box(Modifier.fillMaxSize()) {
        BackButton {
            if (state.value.pageState == PageState.Loaded) {
                viewModel.removeLive()
                navController.navigate(Directions.home.name)
            } else
                navController.navigate(Directions.home.name)
        }


        if (state.value.pageState in arrayListOf(PageState.Loaded))
            LevelBox(state.value.level.toString())
        else
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp), contentAlignment = Alignment.CenterEnd
            ) {
                LivesBox(state.value.lives ?: 0) {
                    navController.navigate(Directions.earn.name)
                }
            }

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
                    Spacer(modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp))

                    state.value.selectedColor?.let {
                        LivesAndAmountRow(
                            lives = state.value.lives ?: 0,
                            amount = state.value.totalColoredEmoji,
                            found = state.value.totalGuessedEmoji,
                            selectedColor = it
                        )
                    }

                    EmojiSlideShow(
                        state.value.emojis,
                        onAction = { emoji -> viewModel.submitAnswer(emoji) })

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
                                    emojis = "",
                                    text = "You have not got all correct emojis.",
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
                    timeCalculator = TAP_COLOR_TIMER
                    selectedColor = null
                    startTimer = false

                    if ((state.value.lives ?: 0) > 0) {
                        InfoBox(
                            title = stringResource(id = R.string.game_color_description_title),
                            text = stringResource(id = R.string.game_tap_color_description),
                            buttonLabel = R.string.go_label,
                        ) {
                            viewModel.loadGame()
                        }
                    } else {
                        InfoBox(
                            title = stringResource(id = R.string.no_lives),
                            text = stringResource(id = R.string.add_lives_to_play_label),
                            buttonLabel = R.string.ok_label
                        ) {
                            navController.navigate(Directions.home.name)
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
                        title = stringResource(id = R.string.game_failed),
                        color = selectedColor,
                        text = stringResource(id = R.string.tap_fail_text_label),
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
                        text = stringResource(id = R.string.tap_won_text_label),
                        nextAction = {
                            viewModel.loadGame()
                        }
                    )
                }
            }
        }
    }
}
