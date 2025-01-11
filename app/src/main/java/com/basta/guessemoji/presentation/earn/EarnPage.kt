package com.basta.guessemoji.presentation.earn

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.basta.guessemoji.R
import com.basta.guessemoji.ui.theme.borderColor
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.OnUserEarnedRewardListener
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

@Composable
fun EarnPage(
    navController: NavController = NavController(LocalContext.current),
    paddingValues: PaddingValues,
    //  viewModel: SettingsViewModel = getViewModel(),
) {
    var rewardedAd: RewardedAd? = null
    val context = LocalContext.current

    var TAG = "Reward"

    var adRequest = AdRequest.Builder().build()
    RewardedAd.load(
        context,
        stringResource(id = R.string.reward_ad_one),
        adRequest,
        object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.toString())
                rewardedAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                rewardedAd = ad
            }
        })

    rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
        override fun onAdClicked() {
            // Called when a click is recorded for an ad.
            Log.d(TAG, "Ad was clicked.")
        }

        override fun onAdDismissedFullScreenContent() {
            // Called when ad is dismissed.
            // Set the ad reference to null so you don't show the ad a second time.
            Log.d(TAG, "Ad dismissed fullscreen content.")
            rewardedAd = null
        }

        override fun onAdFailedToShowFullScreenContent(p0: AdError) {
            // Called when ad fails to show.
            Log.e(TAG, "Ad failed to show fullscreen content.")
            rewardedAd = null
        }

        override fun onAdImpression() {
            // Called when an impression is recorded for an ad.
            Log.d(TAG, "Ad recorded an impression.")
        }

        override fun onAdShowedFullScreenContent() {
            // Called when ad is shown.
            Log.d(TAG, "Ad showed fullscreen content.")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
    ) {


        Text(text = "Reward 1: \uD83E\uDE99 5", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(width = 2.dp, color = borderColor)
            .padding(8.dp)
            .clickable {
                rewardedAd?.let { ad ->
                    ad.show(
                        context.getActivityOrNull()!!,
                        OnUserEarnedRewardListener { rewardItem ->
                            // Handle the reward.
                            val rewardAmount = rewardItem.amount
                            val rewardType = rewardItem.type
                            Log.d(TAG, "User earned the reward. $rewardAmount $rewardType")
                        })
                } ?: run {
                    Log.d(TAG, "The rewarded ad wasn't ready yet.")
                }
            })
        Text(text = "Reward 2: \uD83E\uDE99 5", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(width = 2.dp, color = borderColor)
            .padding(8.dp)
            .clickable { })
        Text(text = "Reward 3: \uD83E\uDE99 5", modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .border(width = 2.dp, color = borderColor)
            .padding(8.dp)
            .clickable { })

    }


}

fun Context.getActivityOrNull(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }

    return null
}
