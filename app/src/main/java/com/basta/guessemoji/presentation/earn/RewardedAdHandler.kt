package com.basta.guessemoji.presentation.earn

import android.app.Activity
import android.util.Log
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.rewarded.RewardItem
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.OnUserEarnedRewardListener

class RewardedAdHandler(private val activity: Activity) {
    private var rewardedAd: RewardedAd? = null
    private val TAG = "RewardedAdHandler"
    private val context = activity.applicationContext

    fun loadAd(adUnitId: String, onCallBack: (AdStatus) -> Unit) {
        val adRequest = AdRequest.Builder().build()
        RewardedAd.load(context, adUnitId, adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, "Ad failed to load: ${adError.message}")
                onCallBack.invoke(AdStatus.FAILED)
                rewardedAd = null
            }

            override fun onAdLoaded(ad: RewardedAd) {
                Log.d(TAG, "Ad was loaded.")
                onCallBack.invoke(AdStatus.READY)
                rewardedAd = ad

                rewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdClicked() {
                        Log.d(TAG, "Ad was clicked.")
                    }

                    override fun onAdDismissedFullScreenContent() {
                        Log.d(TAG, "Ad dismissed fullscreen content.")
                        rewardedAd = null
                    }

                    override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                        super.onAdFailedToShowFullScreenContent(p0)
                        Log.e(TAG, "Ad failed to show fullscreen content: ${p0?.message}")
                        rewardedAd = null
                    }

                    override fun onAdImpression() {
                        Log.d(TAG, "Ad recorded an impression.")
                    }

                    override fun onAdShowedFullScreenContent() {
                        Log.d(TAG, "Ad showed fullscreen content.")
                    }
                }
            }
        })
    }

    fun showAd(onReward: (Int) -> Unit) {
        rewardedAd?.let { ad ->
            ad.show(activity, OnUserEarnedRewardListener { rewardItem ->
                Log.d(
                    TAG,
                    "User earned the reward: Type=${rewardItem.type}, Amount=${rewardItem.amount}"
                )
                onReward(rewardItem.amount)
            })
        } ?: run {
            Log.d(TAG, "The rewarded ad wasn't ready yet.")
        }
    }
}

enum class AdStatus {
    READY, FAILED, LOADING
}
