package com.basta.guessemoji.presentation

import com.basta.guessemoji.domain.repository.UserPreferenceRepository

class UserUseCase(private val userRepo: UserPreferenceRepository) {
     fun getLevel() = userRepo.getUser().level

    fun updateLevel(level: Int) = userRepo.updateLevel(level)

    fun updateCredits(credit: Int) = userRepo.removeCredits(credit)

     fun getCredits() = userRepo.getUser().credit
}