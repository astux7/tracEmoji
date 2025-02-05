package com.basta.guessemoji.presentation.play

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.basta.guessemoji.common.Constants.UNLOCK_LEVEL_TAP
import com.basta.guessemoji.components.CustomAlertDialog
import com.basta.guessemoji.components.TextBoxWithIcon
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.presentation.ui.LivesBox
import com.basta.guessemoji.ui.theme.MenuColor
import com.basta.guessemoji.ui.theme.clockColor
import org.koin.androidx.compose.getViewModel

// https://emojipedia.org/nature#mammals-marsupials
@Composable
fun PlayPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: HomeViewModel = getViewModel()
) {
    var showUnlockDialog by remember { mutableStateOf(false) }
    var comingSoonDialog by remember { mutableStateOf(false) }
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) { viewModel.setUp() }

    Box(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier.fillMaxSize(),
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
                    text = "\uD83C\uDFC6 ${state.value.level} level",
                    modifier = Modifier,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
                Text(
                    text = "  |  ",
                    modifier = Modifier,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
                Text(
                    text = "\uD83E\uDE99 ${state.value.coins}",
                    modifier = Modifier,
                    color = Color.White,
                    fontWeight = FontWeight(700)
                )
            }

            Box(
                Modifier
                    .fillMaxWidth()
                    .height(80.dp), contentAlignment = Alignment.CenterEnd
            ) {
                LivesBox(state.value.lives) { navController.navigate(Directions.earn.name) }
            }
        }

        Box(
            Modifier
                .padding(bottom = 64.dp)
                .size(44.dp)
                .align(Alignment.BottomStart)
                .offset(x = (-3).dp)
                .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                .border(2.dp, Color.White, RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
                .background(MenuColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "☰",
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight(700),
                modifier = Modifier.clickable {
                    navController.navigate(Directions.menu.name)
                }
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
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
                TextBoxWithIcon(Modifier.weight(1f), "Pick a Color", "🎨") {
                    navController.navigate(Directions.game1.name)
                }
                if (state.value.level >= 2) // UNLOCK_LEVEL_TAP)
                    TextBoxWithIcon(Modifier.weight(1f), "Color Tap ", "\uD83D\uDC46") {
                        navController.navigate(Directions.game2.name)
                    }
                else
                    TextBoxWithIcon(Modifier.weight(1f), "Locked", "\uD83D\uDD12") {
                        showUnlockDialog = true
                    }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //  TextBoxWithIcon(Modifier.weight(1f), "Memorize", "\uD83E\uDDE9")                TextBoxWithIcon(Modifier.weight(1f), "Drag", "\uD83E\uDE80")
                // level 100 to unlock
                TextBoxWithIcon(Modifier.weight(1f), "Coming soon", "⌛") { comingSoonDialog = true }
                // level 200 & 50 coins to unlock
                TextBoxWithIcon(Modifier.weight(1f), "Coming soon", "⌛") { comingSoonDialog = true }
            }
        }
    }

    if (showUnlockDialog) {
        CustomAlertDialog(
            title = "\uD83D\uDD13 Unlock Game",
            text = "You can unlock game when you reach \uD83C\uDFC6 level $UNLOCK_LEVEL_TAP",
            buttonText = "Ok",
            onDismissRequest = { showUnlockDialog = false },
            onOkRequest = { showUnlockDialog = false },
        )
    }

    if (comingSoonDialog) {
        CustomAlertDialog(
            title = "⌛Coming Soon",
            text = "Keep an eye on app updates to unlock this game soon",
            buttonText = "Ok",
            onDismissRequest = { comingSoonDialog = false },
            onOkRequest = { comingSoonDialog = false },
        )
    }
}
// emojis have same emoji as a part
// put emoji on color by dragging