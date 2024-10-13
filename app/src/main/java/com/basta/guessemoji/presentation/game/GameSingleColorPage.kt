package com.basta.guessemoji.presentation.game

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.common.utils.toEmoji
import com.basta.guessemoji.components.ColorAnswerPanel
import com.basta.guessemoji.components.EmojiWithFill
import com.basta.guessemoji.components.LottieAnimationLoader
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.ui.theme.MenuColor
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

    LaunchedEffect(Unit) { viewModel.startGame() }

    var isVisibleTimer by remember { mutableStateOf(false) }
    var timeCalculator by remember { mutableIntStateOf(10) }
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
                        delay(400) // 2 seconds delay
                        isVisibleTimer = true
                    }

                    EmojiWithFill(
                        state.value.emojis.joinToString(separator = " "),
                        Color.White
                    )

                    ColorAnswerPanel { color ->
                        viewModel.submitAnswer(color)
                        selectedColor = color
                        startTimer = false
                    }
                }

                PageState.Error -> {
                    state.value.errorType?.let { error ->
                        when (error) {
                            ErrorType.Generic -> LottieAnimationLoader(rawRes = R.raw.generic_error)

                            ErrorType.Network -> LottieAnimationLoader(rawRes = R.raw.no_connection)

                            ErrorType.BadAnswer -> {
                                FailBox(
                                    title = "Oh no!",
                                    color = selectedColor,
                                    emojis = state.value.emojis.joinToString(separator = " "),
                                    nextAction = {
                                        timeCalculator = 10
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
                        Text(
                            text = stringResource(id = R.string.game_one_color_description_title),
                            modifier = Modifier.padding(bottom = 8.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = stringResource(id = R.string.game_one_color_description),
                            modifier = Modifier.padding(bottom = 8.dp),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            modifier = Modifier.fillMaxWidth(),
                            onClick = {
                                timeCalculator = 10
                                selectedColor = null
                                viewModel.loadGame()
                            }) {
                            Text(text = stringResource(id = R.string.go_label))
                        }
                    }
                }

                PageState.End -> {
                    LaunchedEffect(Unit) {
                        delay(400)
                        isVisibleTimer = false
                    }

                    FailBox(
                        title = "Oh no! Timeout",
                        color = selectedColor,
                        emojis = "⏰",
                        nextAction = {
                            timeCalculator = 10
                            selectedColor = null
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
                    timeCalculator = 10

                    CongratsBox(
                        correctColor = selectedColor,
                        emojis = state.value.emojis.joinToString(separator = " "),
                        nextAction = {
                            timeCalculator = 10
                            selectedColor = null
                            viewModel.loadGame()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BackButton(navController: NavController) {
    Box(
        Modifier
            .padding(vertical = 40.dp)
            .offset(x = (-3).dp)
            .size(40.dp)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .border(2.dp, Color.White, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(MenuColor),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "↩",
            color = Color.White,
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight(700),
            modifier = Modifier
                .padding(bottom = 4.dp)
                .clickable {
                    navController.navigate(Directions.play.name)
                }
        )
    }
}

@Composable
fun LevelBox(level: String) {
    Box(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .padding(top = 50.dp, end = 24.dp)
                .align(Alignment.TopEnd)
                .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .border(2.dp, clockColor, RoundedCornerShape(topStart = 16.dp, bottomStart = 16.dp))
                .size(52.dp, 24.dp)
                .background(Color.White),
            contentAlignment = Alignment.CenterStart,
        ) {
            Text(
                text = level,
                color = MaterialTheme.colorScheme.background,
                fontSize = 14.sp,
                textAlign = TextAlign.End,
                fontWeight = FontWeight(800),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 18.dp, start = 2.dp)
                    .align(Alignment.CenterEnd)
            )
        }

        Box(
            Modifier
                .padding(vertical = 40.dp)
                .size(40.dp, 40.dp)
                .align(Alignment.TopEnd),
            contentAlignment = Alignment.TopEnd,
        ) {
            Text(
                text = "\uD83C\uDF1F",
                fontSize = 28.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight(700),
                modifier = Modifier.padding(end = 2.dp, top = 2.dp)
            )
        }
    }
}

@Composable
fun TimerBox(isVisibleTimer: Boolean, timeCalculator: Int) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .height(80.dp)
                .width(1.dp)
        ) // keep height

        AnimatedVisibility(
            visible = isVisibleTimer,
            enter = slideInVertically(),
            exit = slideOutVertically()
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.5f)
                    .offset(y = (-3).dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .border(
                        2.dp,
                        Color.White,
                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .background(clockColor)
                    .padding(top = 30.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                // Center the text horizontally at the top
                Text(
                    text = "⏰ Time left: ${timeCalculator}s",
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .align(Alignment.TopCenter) // Align at the top center of the screen
                )
            }
        }
    }
}

@Composable
fun CongratsBox(correctColor: Color?, emojis: String, nextAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(2.dp, Color.White, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "🎉 Congrats! 🎉",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        EmojiWithFill(emojis)

        Text(
            text = "${correctColor.toEmoji()} is the CORRECT answer",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            onClick = nextAction
        ) {
            Text(text = stringResource(id = R.string.next_label))
        }
    }
}

@Composable
fun FailBox(title: String, color: Color?, emojis: String, nextAction: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .border(2.dp, Color.White, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        EmojiWithFill(emojis)

        color?.let {
            Text(
                text = "${color.toEmoji()} is NOT the CORRECT answer",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        }

        Button(
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth(),
            onClick = nextAction
        ) {
            Text(text = stringResource(id = R.string.next_label))
        }
    }
}