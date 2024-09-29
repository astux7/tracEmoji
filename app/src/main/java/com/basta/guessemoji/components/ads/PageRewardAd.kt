package com.basta.guessemoji.components.ads


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.basta.guessemoji.common.Constants.DEBUG_ON

@Composable
fun PageRewardAd(ad: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = if (DEBUG_ON) {
                    Color.Blue
                } else {
                    Color.Transparent
                }
            )
            .fillMaxWidth()
            .defaultMinSize(minHeight = 50.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (!DEBUG_ON) {

        }
    }
}