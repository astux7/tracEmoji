package com.basta.guessemoji.presentation.play

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.basta.guessemoji.components.TextBoxWithIcon
import com.basta.guessemoji.navigation.Directions

@Composable
fun PlayPage(navController: NavController = NavController(LocalContext.current),
             paddingValues: PaddingValues,
    //  viewModel: SettingsViewModel = getViewModel(),
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .imePadding(),
        verticalArrangement = Arrangement.Top) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextBoxWithIcon(Modifier.weight(1f),"Pick a color", "🎨") {
                navController.navigate(Directions.game1.name)
            }
            TextBoxWithIcon(Modifier.weight(1f),"Game 2", "\uD83E\uDDE9")
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextBoxWithIcon(Modifier.weight(1f),"Pick a shared color", "\uD83E\uDE80")
            TextBoxWithIcon(Modifier.weight(1f),"Game 4", "\uD83E\uDE80")
        }
    }
}

// shared color
// not shared color
// going row and show color need to hit on moving icon with shown color
// emojis have same emoji as a part
// put emoji on color by draging