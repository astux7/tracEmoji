package com.basta.guessemoji.presentation.earn

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.basta.guessemoji.common.Constants.REWARD_COINS
import com.basta.guessemoji.common.Constants.UNLOCK_LEVEL2_COINS
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
    private var lives = 0
    private var hasBoughtTapGame = false

    private val _state = MutableStateFlow(RewardsState(AdStatus.LOADING, totalEarned = rewards, lives = lives, hasBoughtTapGame = hasBoughtTapGame))
    val state: StateFlow<RewardsState> = _state

    fun setUp(activity: Activity?) {
        activity?.let {
            rewardedAdHandler = RewardedAdHandler(activity)
        }
        rewards = userUseCase.getCredits()
        lives = userUseCase.getLives()
        hasBoughtTapGame = userUseCase.hasBoughtTapGame()
        viewModelScope.launch {
            _state.update { it.copy(totalEarned = rewards, lives = lives, hasBoughtTapGame = hasBoughtTapGame) }
        }
    }

    fun buyLives(amount: Int = 1, cost: Int = -REWARD_COINS) {
        userUseCase.updateCredits(cost)
        lives = userUseCase.getLives() + amount
        userUseCase.updateLives(lives)
        rewards += cost
        viewModelScope.launch {
            _state.update { it.copy(totalEarned = rewards, lives = lives) }
        }
    }

    fun boughtTapGame() {
        userUseCase.updateCredits(-UNLOCK_LEVEL2_COINS)
        userUseCase.boughtTapGame()
        hasBoughtTapGame = userUseCase.hasBoughtTapGame()
        viewModelScope.launch {
            _state.update { it.copy(totalEarned = rewards - UNLOCK_LEVEL2_COINS, hasBoughtTapGame = hasBoughtTapGame) }
        }
    }

    fun loadRewardedAd(adUnitId: String) {
        rewardedAdHandler?.loadAd(adUnitId) { adStatus ->
            _state.update { it.copy(isAdReady = adStatus, totalEarned = rewards, lives = lives) }
        }
    }

    fun showRewardedAd() {
        val onReward = { amount: Int ->
            rewards += amount
            userUseCase.addCredits(rewards)
            _state.update { it.copy(totalEarned = rewards) }
        }
        rewardedAdHandler?.showAd(onReward)
    }
}
