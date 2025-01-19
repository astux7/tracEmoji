package com.basta.guessemoji.presentation

import com.basta.guessemoji.domain.repository.UserPreferenceRepository

class UserUseCase(private val userRepo: UserPreferenceRepository) {
    fun getLevel() = userRepo.getUser().level

    fun getUser() = userRepo.getUser()

    fun updateLevel(level: Int) = userRepo.updateLevel(level)

    fun updateCredits(credit: Int) = userRepo.removeCredits(credit)

    fun addCredits(credit: Int) = userRepo.addCredits(credit)

    fun getCredits() = userRepo.getUser().credit
}