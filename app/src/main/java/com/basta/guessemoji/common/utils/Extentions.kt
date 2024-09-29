package com.basta.guessemoji.common.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
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