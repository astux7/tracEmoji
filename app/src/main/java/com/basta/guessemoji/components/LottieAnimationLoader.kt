package com.basta.guessemoji.components

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition

@Composable
fun LottieAnimationLoader(
    modifier: Modifier = Modifier,
    @RawRes rawRes: Int,
    iterations: Int = LottieConstants.IterateForever,
    onProgressComplete: () -> Unit = {},
    isPlaying: Boolean = true,
    scale: ContentScale = ContentScale.FillWidth
) {
    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            rawRes
        )
    )

    val progress by animateLottieCompositionAsState(composition = preloaderLottieComposition)

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = iterations,
        isPlaying = isPlaying
    )

    LottieAnimation(
        composition = preloaderLottieComposition,
        progress = { preloaderProgress },
        modifier = modifier,
        contentScale = scale
    )

    if (progress == 1.toFloat()) {
        onProgressComplete()
    }
}