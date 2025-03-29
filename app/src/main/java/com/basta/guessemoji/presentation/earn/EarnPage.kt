package com.basta.guessemoji.presentation.earn

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.common.Constants.MAX_LIVES
import com.basta.guessemoji.common.Constants.REWARD_COINS
import com.basta.guessemoji.common.Constants.UNLOCK_LEVEL2_COINS
import com.basta.guessemoji.common.EmojiConstants.AD_WATCH_EMOJI
import com.basta.guessemoji.common.EmojiConstants.BROKEN_HEART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.CART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.COIN_EMOJI
import com.basta.guessemoji.common.EmojiConstants.FAIL_EMOJI
import com.basta.guessemoji.common.EmojiConstants.HEART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.LIGHT_BULB_EMOJI
import com.basta.guessemoji.common.EmojiConstants.MARKED_EMOJI
import com.basta.guessemoji.common.EmojiConstants.PLAY_EMOJI
import com.basta.guessemoji.common.EmojiConstants.PLUS_EMOJI
import com.basta.guessemoji.common.EmojiConstants.TIME_EMOJI
import com.basta.guessemoji.common.EmojiConstants.UNLOCKED_EMOJI
import com.basta.guessemoji.presentation.ui.BackButton
import com.basta.guessemoji.ui.theme.ButtonBorder
import com.basta.guessemoji.ui.theme.borderColor
import org.koin.androidx.compose.getViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EarnPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: EarnViewModel = getViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = context as? Activity
    val adUnitId = stringResource(id = R.string.reward_ad_two)

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setUp(activity)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadRewardedAd(adUnitId)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(Modifier.fillMaxSize()) {
        BackButton(navController)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .padding(top = 40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 64.dp)
                    .border(
                        BorderStroke(2.dp, ButtonBorder),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
                    .background(borderColor)
                    .padding(16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Column {
                    Text(
                        stringResource(id = R.string.your_rewards_label),
                        Modifier.fillMaxWidth(),
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W700,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(
                            id = R.string.total_money_label,
                            state.value.totalEarned.toString(),
                            COIN_EMOJI,
                        ),
                        fontWeight = FontWeight.W700,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        stringResource(
                            id = R.string.your_rewards_info_text_label,
                            LIGHT_BULB_EMOJI
                        ), fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(15))
                    .background(borderColor)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.watch_ad_label, AD_WATCH_EMOJI),
                    )
                    Text(
                        text = stringResource(
                            id = R.string.reward_x_label,
                            REWARD_COINS,
                            COIN_EMOJI
                        ),
                        fontSize = 10.sp,
                    )
                }
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    when (state.value.isAdReady) {
                        AdStatus.READY -> {
                            Text(
                                text = stringResource(id = R.string.play_ad_label, PLAY_EMOJI),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .border(
                                        width = 1.dp,
                                        color = ButtonBorder,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(8.dp)
                                    .clickable { viewModel.showRewardedAd() },
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        AdStatus.FAILED -> {
                            Text(
                                text = stringResource(id = R.string.ad_error_label, FAIL_EMOJI),
                                modifier = Modifier.fillMaxWidth(),
                                color = Color.Red,
                                fontSize = 10.sp,
                                textAlign = TextAlign.End
                            )
                        }

                        AdStatus.LOADING -> {
                            Text(
                                text = stringResource(id = R.string.ad_loading_label, TIME_EMOJI),
                                modifier = Modifier.fillMaxWidth(),
                                fontSize = 10.sp,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(15))
                    .background(borderColor)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text(
                        text = stringResource(
                            id = R.string.lives_text_label,
                            if (state.value.lives > 0) HEART_EMOJI else BROKEN_HEART_EMOJI,
                            state.value.lives,
                            MAX_LIVES
                        ),
                    )
                    Text(
                        text = "One live cost $REWARD_COINS $COIN_EMOJI",
                        fontSize = 10.sp,
                    )
                }


                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    if (state.value.lives < MAX_LIVES) {
                        Text(
                            text = stringResource(id = R.string.buy_label, CART_EMOJI),
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .border(
                                    width = 1.dp,
                                    color = ButtonBorder,
                                    shape = RoundedCornerShape(4.dp)
                                )
                                .background(MaterialTheme.colorScheme.background)
                                .padding(8.dp)
                                .clickable(
                                    enabled = (viewModel.state.value.lives
                                        ?: 0) < MAX_LIVES && viewModel.state.value.totalEarned ?: 0 >= REWARD_COINS,
                                    onClick = { viewModel.buyLives() }),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    } else {
                        Text(
                            text = MARKED_EMOJI,
                            modifier = Modifier
                                .padding(4.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .padding(8.dp)
                                .clickable(
                                    enabled = false, onClick = {}),
                            textAlign = TextAlign.End,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(15))
                    .border(width = 1.dp, color = borderColor, shape = RoundedCornerShape(15))
                    .background(borderColor)
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {

                Column(Modifier.weight(1f)) {
                    Text(
                        text = stringResource(id = R.string.unlock_level_2_game, UNLOCKED_EMOJI),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Unlock the game cost $UNLOCK_LEVEL2_COINS $COIN_EMOJI",
                        fontSize = 10.sp,
                    )
                }

                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                    when {
                        viewModel.state.value.hasBoughtTapGame -> {
                            Text(
                                text = MARKED_EMOJI,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .padding(8.dp)
                                    .clickable(
                                        enabled = false, onClick = {}),
                                textAlign = TextAlign.End,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                        viewModel.state.value.totalEarned ?: 0 >= UNLOCK_LEVEL2_COINS -> {
                            Text(
                                text = stringResource(
                                    id = R.string.buy_label,
                                    CART_EMOJI
                                ),
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .border(
                                        width = 1.dp,
                                        color = ButtonBorder,
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(8.dp)
                                    .clickable(
                                        enabled = viewModel.state.value.totalEarned >= UNLOCK_LEVEL2_COINS,
                                        onClick = { viewModel.boughtTapGame() }),
                                textAlign = TextAlign.End
                            )
                        }
                        else -> {
                            val shortMoney = UNLOCK_LEVEL2_COINS - viewModel.state.value.totalEarned
                            Text(
                                text = stringResource(
                                    id = R.string.missing_label,
                                    shortMoney,
                                    COIN_EMOJI
                                ),
                                modifier = Modifier
                                    .padding(4.dp)
                                    .padding(8.dp)
                                    .clickable(
                                        enabled = false, onClick = {}),
                                textAlign = TextAlign.End,
                                fontSize = 10.sp,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                }
            }
        }
    }
}
