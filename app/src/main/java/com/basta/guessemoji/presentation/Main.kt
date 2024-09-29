package com.basta.guessemoji.presentation

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.navigation.buildTheGraph

@Composable
fun MainScreen() {

    val navController = rememberNavController()
    Scaffold(
       // bottomBar = { BottomBar(navController = navController) }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Directions.home.name,
        ) {
            buildTheGraph(navController, padding)
        }
    }
}