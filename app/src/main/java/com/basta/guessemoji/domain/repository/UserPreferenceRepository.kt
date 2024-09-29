package com.basta.guessemoji.domain.repository

interface UserPreferenceRepository {
    suspend fun getUser(): Int
    suspend fun addCredits(credit: Int)
    suspend fun removeCredits(credit: Int)
    fun wipeData()
}