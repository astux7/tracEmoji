package com.basta.guessemoji.presentation.earn

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basta.guessemoji.presentation.UserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class EarnViewModel(
    private val userUseCase: UserUseCase
) : ViewModel() {
    private var rewardedAdHandler: RewardedAdHandler? = null
    private var rewards = 0

    private val _state = MutableStateFlow(RewardsState(AdStatus.LOADING, 0))
    val state: StateFlow<RewardsState> = _state

    fun setUp(activity: Activity?) {
        activity?.let {
            rewardedAdHandler = RewardedAdHandler(activity)
        }
        rewards = userUseCase.getCredits()
        viewModelScope.launch {
            _state.update { RewardsState(totalEarned = rewards) }
        }
    }

    fun loadRewardedAd(adUnitId: String) {
        rewardedAdHandler?.loadAd(adUnitId) { adStatus ->
            _state.update { RewardsState(isAdReady = adStatus, totalEarned = rewards) }
        }
    }

    fun showRewardedAd() {
        val onReward = { amount: Int ->
            rewards += amount
            userUseCase.addCredits(rewards)
            _state.update { RewardsState(totalEarned = rewards) }
        }
        rewardedAdHandler?.showAd(onReward)
    }
}
