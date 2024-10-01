package com.basta.guessemoji.presentation.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.components.ColorAnswerPanel
import com.basta.guessemoji.components.CustomButton
import com.basta.guessemoji.components.EmojiWithFill
import com.basta.guessemoji.components.LottieAnimationLoader
import com.basta.guessemoji.ui.theme.clockColor
import kotlinx.coroutines.delay
import org.koin.androidx.compose.getViewModel

@Composable
fun Game1Page(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: GameViewModel = getViewModel()
) {
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.startGame()
    }

    var isVisibleTimer by remember { mutableStateOf(false) }
    var timeCalculator by remember { mutableIntStateOf(1) }
    var startTimer by remember { mutableStateOf(false) }

    LaunchedEffect(startTimer) {
        if (startTimer) {
            // Run the timer until 10 seconds is reached
            while (timeCalculator < 10) {
                delay(1000) // Delay for 1 second
                timeCalculator++
            }
            startTimer = false
            viewModel.gameTimeOut()
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

       Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
           Spacer(modifier = Modifier.height(50.dp).width(1.dp)) // keep height
           AnimatedVisibility(
               visible = isVisibleTimer,
               enter = slideInVertically(),
               exit = slideOutVertically()
           ) {
               Box(
                   modifier = Modifier
                       .height(50.dp)
                       .fillMaxWidth(0.5f)
                       .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                       .background(clockColor),
                   contentAlignment = Alignment.Center
               ) {
                   // Center the text horizontally at the top
                   Text(
                       text = "⏰ Time left: ${timeCalculator.toString()}s",
                       color = Color.White,
                       modifier = Modifier
                           .padding(top = 16.dp)
                           .align(Alignment.TopCenter) // Align at the top center of the screen
                   )
               }
           }
       }

        Spacer(modifier = Modifier.padding(top = 16.dp))
        when (state.value.pageState) {
            PageState.Loading -> LottieAnimationLoader(rawRes = R.raw.loading)
            PageState.Loaded -> {
                timeCalculator = 1
                startTimer = true
                LaunchedEffect(Unit) {
                    delay(400) // 2 seconds delay
                    isVisibleTimer = true
                }

                EmojiWithFill(
                    state.value.emojis.joinToString(separator = " "),
                    Color.White
                )

                ColorAnswerPanel { color ->
                    viewModel.submitAnswer(color)
                    startTimer = false
                }

            }

            PageState.Error -> {
                Text(text = "Error")
                state.value.errorType?.let { error ->
                    when (error) {
                        ErrorType.Generic -> LottieAnimationLoader(rawRes = R.raw.generic_error)

                        ErrorType.Network -> LottieAnimationLoader(rawRes = R.raw.no_connection)

                        ErrorType.BadAnswer -> {

                        }
                    }
                }
            }

            PageState.Start -> {
                Text(text = stringResource(id = R.string.game_one_color_description))
                CustomButton(modifier = Modifier, label = R.string.play_label) {
                    viewModel.loadGame()
                }
            }
            PageState.End -> {
                LaunchedEffect(Unit) {
                    delay(400)
                    isVisibleTimer = false
                }
                startTimer = false
                timeCalculator = 0
            }

            PageState.Success -> {
                LaunchedEffect(Unit) {
                    delay(400)
                    isVisibleTimer = false
                }
                EmojiWithFill(
                    state.value.emojis.joinToString(separator = " "),
                )
                LottieAnimationLoader(rawRes = R.raw.loading)
            }
        }

    }
}
