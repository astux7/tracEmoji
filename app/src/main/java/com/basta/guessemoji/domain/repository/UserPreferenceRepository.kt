package com.basta.guessemoji.domain.repository

import com.basta.guessemoji.domain.model.User

interface UserPreferenceRepository {
    fun getUser(): User
    fun addCredits(credit: Int)
    fun removeCredits(credit: Int)
    fun updateLevel(level: Int)
    fun updateLives(lives: Int)
    fun wipeData()
}