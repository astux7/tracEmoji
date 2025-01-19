package com.basta.guessemoji.presentation.earn

import android.app.Activity
import android.app.Application
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.ui.theme.borderColor
import org.koin.androidx.compose.getViewModel

@Composable
fun EarnPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    viewModel: EarnViewModel = getViewModel(),
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val activity = context as? Activity
    val adUnitId = stringResource(id = R.string.reward_ad_one)

    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.setUp(activity)
    }

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.loadRewardedAd(adUnitId)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text("Your Rewards", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Total earned: " + state.value.totalEarned.toString() + " \uD83E\uDE99")
        Spacer(modifier = Modifier.height(16.dp))

        when (state.value.isAdReady) {
            AdStatus.READY -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(width = 2.dp, color = borderColor)
                        .padding(8.dp)
                        .clickable { viewModel.showRewardedAd() },
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "\uD83C\uDFA5 Watch ad",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Start
                    )
                    Text(
                        text = "Reward: 5 \uD83E\uDE99",
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.End
                    )
                }
            }

            AdStatus.FAILED -> {
                Text(
                    text = "\uD83D\uDE2C Ops! Something went wrong - come back later",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(width = 2.dp, color = borderColor)
                        .padding(8.dp)
                )
            }

            AdStatus.LOADING -> {
                Text(
                    text = "⏳ Loading ...", modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .border(width = 2.dp, color = borderColor)
                        .padding(8.dp)
                )
            }
        }
    }
}
