package com.basta.guessemoji.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

fun Context.onLinkClick(url: String) {
    val browserIntent =
        Intent(
            Intent.ACTION_VIEW,
            Uri.parse(url)
        )
    this.startActivity(browserIntent)
}

fun NavController.back() = this.popBackStack()

fun Color?.toEmoji() : String = when(this) {
    Color.Red -> "\uD83D\uDFE5"
    Color.Yellow -> "\uD83D\uDFE8"
    Color.Green -> "\uD83D\uDFE9"
    Color.Blue -> "\uD83D\uDFE6"
    else -> ""
}