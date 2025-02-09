package com.basta.guessemoji.domain.repository

import com.basta.guessemoji.domain.model.User

interface UserPreferenceRepository {
    fun getUser(): User
    fun addCredits(credit: Int)
    fun updateCredits(credit: Int)
    fun updateLevel(level: Int)
    fun updateLastSeen()
    fun updateLives(lives: Int)
    fun removeLives(lives: Int)
    fun wipeData()
}