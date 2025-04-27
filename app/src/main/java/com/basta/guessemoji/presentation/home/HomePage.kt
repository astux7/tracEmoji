package com.basta.guessemoji.presentation.home

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.common.Constants.DEBUG_ON
import com.basta.guessemoji.common.Constants.DEBUG_ON_MENU_TAP
import com.basta.guessemoji.common.Constants.UNLOCK_LEVEL2_COINS
import com.basta.guessemoji.common.Constants.UNLOCK_LEVEL2_TAP
import com.basta.guessemoji.common.EmojiConstants.COIN_EMOJI
import com.basta.guessemoji.common.EmojiConstants.LOCKED_EMOJI
import com.basta.guessemoji.common.EmojiConstants.MENU_EMOJI
import com.basta.guessemoji.common.EmojiConstants.PALETTE_EMOJI
import com.basta.guessemoji.common.EmojiConstants.SPACER_EMOJI
import com.basta.guessemoji.common.EmojiConstants.TAP_EMOJI
import com.basta.guessemoji.common.EmojiConstants.TIME_EMOJI
import com.basta.guessemoji.common.EmojiConstants.TROPHY_EMOJI
import com.basta.guessemoji.common.EmojiConstants.UNLOCKED_EMOJI
import com.basta.guessemoji.common.TopMenuIconStyle
import com.basta.guessemoji.common.TopMenuItemStyle
import com.basta.guessemoji.common.utils.OnLifecycleEvent
import com.basta.guessemoji.components.CustomAlertDialog
import com.basta.guessemoji.components.TextBoxWithIcon
import com.basta.guessemoji.components.ads.PageAdBanner
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.presentation.ui.LivesBox
import com.basta.guessemoji.ui.theme.MenuColor
import com.basta.guessemoji.ui.theme.clockColor
import org.koin.androidx.compose.getViewModel

// https://emojipedia.org/nature#mammals-marsupials
@Composable
fun HomePage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = getViewModel()
) {
    var showUnlockDialog by remember { mutableStateOf(false) }
    var comingSoonDialog by remember { mutableStateOf(false) }
    var gameName by remember { mutableStateOf("") }
    val state = viewModel.state.collectAsState()

    OnLifecycleEvent { _, event ->
        when (event) {
            Lifecycle.Event.ON_RESUME -> {
                viewModel.setUp()
            }
            else -> {}
        }
    }

    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(top = 54.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextBoxWithIcon(
                    Modifier.weight(1f),
                    stringResource(R.string.home_pick_color_label),
                    PALETTE_EMOJI
                ) {
                    navController.navigate(Directions.game1.name)
                }
                if (state.value.level >= UNLOCK_LEVEL2_TAP || DEBUG_ON_MENU_TAP || state.value.boughtTapGame)
                    TextBoxWithIcon(
                        Modifier.weight(1f),
                        stringResource(R.string.home_color_tap_label),
                        TAP_EMOJI
                    ) {
                        navController.navigate(Directions.game2.name)
                    }
                else
                    TextBoxWithIcon(
                        Modifier.weight(1f),
                        stringResource(R.string.home_locked_label),
                        LOCKED_EMOJI
                    ) {
                        gameName = "Tap Game"
                        showUnlockDialog = true
                    }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //  TextBoxWithIcon(Modifier.weight(1f), "Memorize", "\uD83E\uDDE9")
                //  TextBoxWithIcon(Modifier.weight(1f), "Drag", "\uD83E\uDE80")

                // level 100 to unlock
                TextBoxWithIcon(
                    Modifier.weight(1f),
                    stringResource(id = R.string.home_coming_soon_label),
                    TIME_EMOJI
                ) { comingSoonDialog = true }
                // level 200 & 50 coins to unlock
                TextBoxWithIcon(
                    Modifier.weight(1f),
                    stringResource(id = R.string.home_coming_soon_label),
                    TIME_EMOJI
                ) { comingSoonDialog = true }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (DEBUG_ON)
                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .background(Color.Red)
                    )
                else
                    PageAdBanner(R.string.home_ad_banner)
            }
        }

        Box(
            Modifier
                .padding(bottom = 64.dp)
                .size(48.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-3).dp)
                .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                .border(2.dp, Color.White, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                .background(MenuColor)
                .clickable {
                    navController.navigate(Directions.menu.name)
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = MENU_EMOJI,
                style = TopMenuIconStyle(),
                modifier = Modifier
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopCenter
        ) {
            Row(
                Modifier
                    .height(80.dp)
                    .fillMaxWidth(0.55f)
                    .offset(y = (-3).dp)
                    .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                    .border(
                        2.dp,
                        Color.White,
                        RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    )
                    .background(clockColor)
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = stringResource(
                        id = R.string.level_text_label,
                        TROPHY_EMOJI,
                        state.value.level
                    ),
                    style = TopMenuItemStyle(),
                )
                Text(
                    text = SPACER_EMOJI,
                    style = TopMenuItemStyle(),
                )
                Text(
                    text = stringResource(
                        id = R.string.coins_text_label,
                        COIN_EMOJI,
                        state.value.coins
                    ),
                    style = TopMenuItemStyle(),
                )
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp), contentAlignment = Alignment.CenterEnd
            ) {
                LivesBox(state.value.lives) {
                    navController.navigate(Directions.earn.name)
                }
            }
        }
    }

    if (showUnlockDialog) {
        CustomAlertDialog(
            title = UNLOCKED_EMOJI + stringResource(id = R.string.unlock_game_label, gameName),
            text = stringResource(
                id = R.string.unlock_game_text_label,
                TROPHY_EMOJI,
                UNLOCK_LEVEL2_TAP,
                UNLOCK_LEVEL2_COINS,
                COIN_EMOJI
            ),
            buttonText = stringResource(id = R.string.ok_label),
            onDismissRequest = { showUnlockDialog = false },
            onOkRequest = { showUnlockDialog = false },
            onBuyRequest = { navController.navigate(Directions.earn.name) }
        )
    }

    if (comingSoonDialog) {
        CustomAlertDialog(
            title = TIME_EMOJI + stringResource(id = R.string.home_coming_soon_label),
            text = stringResource(id = R.string.coming_soon_text_label),
            buttonText = stringResource(id = R.string.ok_label),
            onDismissRequest = { comingSoonDialog = false },
            onOkRequest = { comingSoonDialog = false },
        )
    }
}
// emojis have same emoji as a part
// put emoji on color by dragging