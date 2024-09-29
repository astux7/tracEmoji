package com.basta.guessemoji.presentation.play

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
        .padding(paddingValues)) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextBoxWithIcon(Modifier.weight(1f),"Game 1", "\uD83E\uDDE9"){
                navController.navigate(Directions.game1.name)
            }
            TextBoxWithIcon(Modifier.weight(1f),"Game 2", "\uD83E\uDE80")
        }
    }
}