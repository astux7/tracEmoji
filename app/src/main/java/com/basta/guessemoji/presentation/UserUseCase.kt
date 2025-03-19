package com.basta.guessemoji.presentation

import com.basta.guessemoji.domain.repository.UserPreferenceRepository

class UserUseCase(private val userRepo: UserPreferenceRepository) {
    init {
        val lastSeen = getLastSeen()
        when {
            isWithinLastXHours(lastSeen, 24) -> {
                updateLastSeen()
                updateLevel(3)
            }
            isWithinLastXHours(lastSeen, 12) -> {
                updateLastSeen()
                updateLevel(3)
            }
            isWithinLastXHours(lastSeen, 1) -> {
                updateLastSeen()
                updateLevel(3)
            }
            else -> {}
        }
    }



    fun getLevel() = userRepo.getUser().level

    fun updateLevel(level: Int) = userRepo.updateLevel(level)

    fun removeLive(live: Int) = userRepo.removeLives(live)

    fun updateLives(live: Int) = userRepo.updateLives(live)

    fun updateCredits(credit: Int) = userRepo.updateCredits(credit)

    fun addCredits(credit: Int) = userRepo.addCredits(credit)

    fun getCredits() = userRepo.getUser().credit

    fun getLives() = userRepo.getUser().lives

    fun hasBoughtTapGame() = userRepo.getUser().boughtTapGame

    fun boughtTapGame() = userRepo.setBoughtTapGame()

    private fun getLastSeen() = userRepo.getUser().lastSeen

    private fun updateLastSeen() = userRepo.updateLastSeen()

    private fun isWithinLastXHours(lastSeenMillis: Long, hours: Int): Boolean {
        val currentTimeMillis = System.currentTimeMillis() // Get current time
     //   val twentyFourHoursMillis = hours * 60 * 60 * 1000 // 24 hours in milliseconds
        val twentyFourHoursMillis = 5 * 1000 // 24 hours in milliseconds // TODO: faked?

        // Check if lastSeen is within the last 24 hours
        return lastSeenMillis in (currentTimeMillis - twentyFourHoursMillis)..currentTimeMillis
    }

}