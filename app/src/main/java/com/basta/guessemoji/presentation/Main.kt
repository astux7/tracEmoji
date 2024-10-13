package com.basta.guessemoji.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.basta.guessemoji.navigation.Directions
import com.basta.guessemoji.navigation.buildTheGraph

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets,
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Directions.play.name,
        ) {
            buildTheGraph(navController, padding)
        }
    }
}