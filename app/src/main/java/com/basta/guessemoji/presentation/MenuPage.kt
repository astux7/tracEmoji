package com.basta.guessemoji.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import com.basta.guessemoji.components.TextBoxWithIcon
import com.basta.guessemoji.navigation.Directions

// https://stackoverflow.com/questions/32413731/color-for-unicode-emoji
// TraceMoji

@Composable
fun MenuPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues
) {
    Column(Modifier.padding(paddingValues)) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                TextBoxWithIcon(Modifier.fillMaxWidth(), "Back", "⬅\uFE0F") {
                    navController.navigate(Directions.play.name)
                }
                TextBoxWithIcon(Modifier.fillMaxWidth(), "Earn", "\uD83C\uDF81") {
                    navController.navigate(Directions.earn.name)
                }
            }
            Column(
                Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
            ) {
                TextBoxWithIcon(
                    Modifier.fillMaxWidth(),
                    "Credits",
                    "\uD83D\uDC64"
                ) //"\uD83C\uDFC6")
                TextBoxWithIcon(Modifier.fillMaxWidth(), "Settings", "⚙\uFE0F") {
                    navController.navigate(Directions.settings.name)
                }
            }
        }
    }
}

// extend timer +5s
// skip level
// open 1 emoji which user chooses