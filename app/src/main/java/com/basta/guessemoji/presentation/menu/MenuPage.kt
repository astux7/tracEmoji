package com.basta.guessemoji.presentation.menu

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.basta.guessemoji.R
import com.basta.guessemoji.common.Constants
import com.basta.guessemoji.common.Constants.MAX_LIVES
import com.basta.guessemoji.common.EmojiConstants
import com.basta.guessemoji.common.EmojiConstants.BROKEN_HEART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.CART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.COIN_EMOJI
import com.basta.guessemoji.common.EmojiConstants.HEART_EMOJI
import com.basta.guessemoji.common.EmojiConstants.PLUS_EMOJI
import com.basta.guessemoji.common.EmojiConstants.TROPHY_EMOJI
import com.basta.guessemoji.common.ProfileIconStyle
import com.basta.guessemoji.common.ProfileLevelStyle
import com.basta.guessemoji.common.utils.onLinkClick
import com.basta.guessemoji.components.CustomButton
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.presentation.ui.BackButton
import com.basta.guessemoji.ui.theme.ButtonBorder
import com.basta.guessemoji.ui.theme.borderColor
import org.koin.androidx.compose.getViewModel

// https://stackoverflow.com/questions/32413731/color-for-unicode-emoji
@Composable
fun MenuPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: MenuViewModel = getViewModel(),
) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        BackButton { navController.navigate(Directions.home.name) }

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(Modifier, contentAlignment = Alignment.TopStart) {

                Box(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(horizontal = 64.dp)
                        .border(
                            BorderStroke(2.dp, ButtonBorder),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
                        .background(borderColor)
                        .padding(4.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column(
                        Modifier.padding(vertical = 4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {

                        Box(
                            modifier = Modifier
                                .padding(4.dp)
                                .size(80.dp)
                                .border(
                                    BorderStroke(2.dp, ButtonBorder),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clip(RoundedCornerShape(16.dp))
                                .background(color = MaterialTheme.colorScheme.background)
                                .aspectRatio(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                Modifier.fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Top
                            ) {
                                Text(
                                    text = viewModel.getProfileIcon(),
                                    style = ProfileIconStyle(),
                                )
                            }
                        }

                        Text(
                            text = stringResource(
                                id = R.string.level_text_label,
                                TROPHY_EMOJI,
                                viewModel.getUserLevel()
                            ),
                            style = ProfileLevelStyle(),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Row(
                            Modifier
                                .padding(all = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.coins_text_label,
                                    COIN_EMOJI,
                                    viewModel.getUserCredits()
                                ),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Text(
                                text = stringResource(id = R.string.add_label, PLUS_EMOJI),
                                modifier = Modifier
                                    .clickable { navController.navigate(Directions.earn.name) }
                                    .clip(RoundedCornerShape(4.dp))
                                    .border(1.dp, ButtonBorder, shape = RoundedCornerShape(4.dp))
                                    .background(MaterialTheme.colorScheme.background)
                                    .padding(8.dp),
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Row(
                            Modifier
                                .padding(all = 8.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(
                                    id = R.string.lives_text_label,
                                    if (viewModel.getUserLives() > 0) HEART_EMOJI else BROKEN_HEART_EMOJI,
                                    viewModel.getUserLives(),
                                    MAX_LIVES
                                ),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            if (viewModel.getUserLives() < MAX_LIVES) {

                                Text(
                                    text = stringResource(id = R.string.buy_label, CART_EMOJI),
                                    modifier = Modifier
                                        .clickable { navController.navigate(Directions.earn.name) }
                                        .clip(RoundedCornerShape(4.dp))
                                        .border(
                                            1.dp,
                                            ButtonBorder,
                                            shape = RoundedCornerShape(4.dp)
                                        )
                                        .background(MaterialTheme.colorScheme.background)
                                        .padding(8.dp),
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center
                                )
                            } else
                                Text(
                                    text = EmojiConstants.MARKED_EMOJI,
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
            }

            CustomButton(
                Modifier
                    .padding(horizontal = 6.dp)
                    .padding(top = 6.dp),
                R.string.rewards_page_label
            ) {
                navController.navigate(Directions.earn.name)
            }

            CustomButton(Modifier.padding(horizontal = 6.dp), R.string.privacy_policy_label) {
                context.onLinkClick(Constants.BASE_URL_POLICY)
            }

            Text(
                text = stringResource(id = R.string.app_version_label, viewModel.appVersion),
                Modifier.padding(horizontal = 16.dp),
                fontSize = 10.sp
            )
        }
    }
}

// extend timer +5s
// skip level
// open 1 emoji which user chooses