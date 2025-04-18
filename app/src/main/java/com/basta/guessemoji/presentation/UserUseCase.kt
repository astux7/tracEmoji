package com.basta.guessemoji.presentation

import com.basta.guessemoji.common.Constants.DEBUG_RENEW_LAST_SEEN_5_S
import com.basta.guessemoji.domain.repository.UserPreferenceRepository

class UserUseCase(private val userRepo: UserPreferenceRepository) {
    init {
        checkForUpdates()
    }

    fun checkForUpdates() {
        val lastSeen = getLastSeen()
        if (DEBUG_RENEW_LAST_SEEN_5_S && isWithinLastXHours(lastSeen, 2)) {
            updateLastSeen()
            updateLives(1)
        }
        when {
            isWithinLastXHours(lastSeen, 1) -> {
                updateLastSeen()
                updateLives(0)
            }

            isWithinLastXHours(lastSeen, 2) -> {
                updateLastSeen()
                updateLives(1)
            }

            isWithinLastXHours(lastSeen, 3) -> {
                updateLastSeen()
                updateLives(2)
            }

            else -> {
                updateLastSeen()
                updateLives(3)
            }
        }
    }

    fun getLevel() = userRepo.getUser().level

    fun updateLevel(level: Int) = userRepo.updateLevel(level)

    fun removeLive(live: Int) = userRepo.removeLives(live)

    fun updateLives(live: Int) = userRepo.updateLives(live)

    fun updateCredits(credit: Int) = userRepo.updateCredits(credit)

    fun getCredits() = userRepo.getUser().credit

    fun getLives() = userRepo.getUser().lives

    fun hasBoughtTapGame() = userRepo.getUser().boughtTapGame

    fun boughtTapGame() = userRepo.setBoughtTapGame()

    private fun getLastSeen() = userRepo.getUser().lastSeen

    private fun updateLastSeen() = userRepo.updateLastSeen()

    private fun isWithinLastXHours(lastSeenMillis: Long, hours: Int): Boolean {
        val currentTimeMillis = System.currentTimeMillis() // Get current time
        val twentyFourHoursMillis = if (DEBUG_RENEW_LAST_SEEN_5_S)
            5 * 1000 // only for testing 5s
        else
            hours * 60 * 60 * 1000

        // Check if lastSeen is within the last 24 hours
        return lastSeenMillis in (currentTimeMillis - twentyFourHoursMillis)..currentTimeMillis
    }

}