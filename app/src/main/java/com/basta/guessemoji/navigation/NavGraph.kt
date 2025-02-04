package com.basta.guessemoji.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.basta.guessemoji.presentation.MenuPage
import com.basta.guessemoji.presentation.earn.EarnPage
import com.basta.guessemoji.presentation.game.colortap.ColorTapGamePage
import com.basta.guessemoji.presentation.game.pickcolor.PickAColorGamePage
import com.basta.guessemoji.presentation.play.PlayPage
import com.basta.guessemoji.presentation.profile.ProfileScreen
import com.basta.guessemoji.presentation.settings.SettingsPage

fun NavGraphBuilder.buildTheGraph(navController: NavController, padding: PaddingValues) {
    composable(Directions.menu.name) {
        MenuPage(navController, padding)
    }
    composable(Directions.game2.name) {
        ColorTapGamePage(navController, padding)
    }

    composable(Directions.settings.name) {
        SettingsPage(navController, padding)
    }

    composable(Directions.earn.name) {
        EarnPage(navController, padding)
    }

    composable(Directions.home.name) {
        PlayPage(navController, padding)
    }

    composable(Directions.game1.name) {
        PickAColorGamePage(navController, padding)
    }
    composable(Directions.profile.name) {
        ProfileScreen(navController, padding)
    }
}