package com.basta.guessemoji.presentation.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.basta.guessemoji.components.EmojiWithFill

@Composable
fun Game1Page(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues
) {
    // YRGB
    Column(
        Modifier
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(top = 16.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        EmojiWithFill(
//            "\uD83C\uDF35 \uD83C\uDF53 \uD83D\uDC2C \uD83C\uDFB2",
//            Color.White
//        ) // yellow dont have

        EmojiWithFill(
            "\uD83E\uDDA9 \uD83C\uDF45 \uD83D\uDC09 \uD83E\uDDCA",
            Color.White
        ) // yellow dont have


        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "\uD83D\uDFE8",
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { },
                fontSize = 80.sp
            )
            Text(
                text = "\uD83D\uDFE6",
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { },
                fontSize = 80.sp
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "\uD83D\uDFE5",
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { },
                fontSize = 80.sp
            )
            Text(
                text = "\uD83D\uDFE9",
                modifier = Modifier
                    .padding(4.dp)
                    .clickable { },
                fontSize = 80.sp
            )
        }
    }
}
