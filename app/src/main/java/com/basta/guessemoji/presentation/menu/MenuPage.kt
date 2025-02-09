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
import com.basta.guessemoji.common.utils.onLinkClick
import com.basta.guessemoji.components.CustomButton
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.presentation.ui.BackButton
import com.basta.guessemoji.ui.theme.borderColor
import org.koin.androidx.compose.getViewModel

// https://stackoverflow.com/questions/32413731/color-for-unicode-emoji
// TraceMoji

@Composable
fun MenuPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: MenuViewModel = getViewModel(),
) {
    val context = LocalContext.current

    Box(Modifier.fillMaxSize()) {
        BackButton(navController)

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
                            BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .clip(RoundedCornerShape(16.dp))
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
                                    BorderStroke(2.dp, borderColor),
                                    shape = RoundedCornerShape(16.dp)
                                )
                                .clip(RoundedCornerShape(16.dp))
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
                                    fontSize = 45.sp,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight(700)
                                )
                            }
                        }

                        Text(
                            text = "\uD83C\uDFC6 level ${viewModel.getUserLevel()}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )

                        Row(
                            Modifier.padding(all = 8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "\uD83E\uDE99 coins ${viewModel.getUserCredits()} ",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Text(
                                text = "\uD83D\uDED2 Buy",
                                modifier = Modifier
                                    .clickable { navController.navigate(Directions.earn.name) }
                                    .border(2.dp, borderColor, shape = RoundedCornerShape(4.dp))
                                    .padding(8.dp),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }

                        Row(
                            Modifier.padding(all = 8.dp).fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "❤\uFE0F lives ${viewModel.getUserLives()} / $MAX_LIVES",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                modifier = Modifier.padding(vertical = 4.dp)
                            )

                            Text(
                                text = "➕ Add",
                                modifier = Modifier
                                    .clickable { navController.navigate(Directions.earn.name) }
                                    .border(2.dp, borderColor, shape = RoundedCornerShape(4.dp))
                                    .padding(8.dp),
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        }
                    }

                }
            }

            CustomButton(Modifier.padding(6.dp), R.string.privacy_policy_label) {
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