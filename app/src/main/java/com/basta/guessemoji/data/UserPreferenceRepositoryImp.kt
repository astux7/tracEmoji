package com.basta.guessemoji.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.basta.guessemoji.common.Constants.INITIAL_CREDITS
import com.basta.guessemoji.common.Constants.INITIAL_LEVEL
import com.basta.guessemoji.common.Constants.MAX_LIVES
import com.basta.guessemoji.domain.model.User
import com.basta.guessemoji.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch

class UserPreferenceRepositoryImp(
    context: Context,
    private val externalScope: CoroutineScope
) : UserPreferenceRepository {
    companion object {
        const val PREFERENCE_KEY_PREFIX = "pref_user_"
        const val APP_DATA_STORE = "trace_emoji"
        const val CREDITS_TOTAL = "credits_total"
        const val GAME_LEVEL = "game_level"
        const val USER_LIVES = "lives"
        const val LAST_SEEN = "last_seen"
        const val BOUGHT_TAP_GAME = "bought_tap_game"
        const val FIRST_INSTALL = "first_install"
    }

    private var cachedMap: MutablePreferences? = null
    private val Context.dataStore by preferencesDataStore(APP_DATA_STORE)
    private val dataStore: DataStore<Preferences> = context.dataStore

    private val cacheMapInitialized = CompletableDeferred<Boolean>()

    private suspend fun waitForCacheMapInitialization() {
        cacheMapInitialized.await()
    }

    // In init block:
    init {
        externalScope.launch {
            setUpCacheMap()
            waitForCacheMapInitialization()  // Block until cacheMap is initialized

            if (isFirstInstall() == null) {
                setFirstInstall()
                if (getLives() == null)
                    updateLives(MAX_LIVES, true)
                if (getLevel() == null)
                    updateLevel(INITIAL_LEVEL)
                if (getCredit() == null)
                    updateCredits(INITIAL_CREDITS, true)
                if (getBoughtTapGame() == null)
                    setBoughtTapGame(false)
            }
            getUser()
        }
    }

    private fun isFirstInstall() =
        cachedMap?.get(getBoolPreferences(PREFERENCE_KEY_PREFIX + FIRST_INSTALL))

    private fun setFirstInstall() {
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getBoolPreferences(PREFERENCE_KEY_PREFIX + FIRST_INSTALL)] =
                        false
                }
                cachedMap?.set(
                    getBoolPreferences(PREFERENCE_KEY_PREFIX + FIRST_INSTALL),
                    false
                )
            } catch (_: Exception) {
            }
        }
    }

    private suspend fun setUpCacheMap() {
        try {
            cachedMap = getUserInto().first().toMutablePreferences()
            cacheMapInitialized.complete(true)
        } catch (_: Exception) {
            cacheMapInitialized.complete(false)
        }
    }

    private fun getCredit() =
        cachedMap?.get(getIntPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL))

    private fun getLevel() =
        cachedMap?.get(getIntPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL))

    private fun getLastSeen() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + LAST_SEEN))
            ?: System.currentTimeMillis().toString()

    private fun getLives() =
        cachedMap?.get(getIntPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES))

    private fun getBoughtTapGame() =
        cachedMap?.get(getBoolPreferences(PREFERENCE_KEY_PREFIX + BOUGHT_TAP_GAME))

    override fun getUser(): User {
        val level: Int? = getLevel()
        val credit: Int? = getCredit()
        val lives: Int? = getLives()
        val lastSeen: String = getLastSeen()
        val boughtTapGame: Boolean = (getBoughtTapGame() == true)

        Log.d(
            "TEST",
            "Credit: $credit, level: $level, lives: $lives , last seen : $lastSeen , bought tap game: $boughtTapGame"
        )

        return User(
            credit = credit ?: 0,
            level = level ?: 0,
            lives = lives ?: 0,
            lastSeen = lastSeen.toLongOrNull() ?: 0,
            boughtTapGame = boughtTapGame
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getUserInto() = dataStore.data.catch { e ->
        if (e is IOException) {
            emit(emptyPreferences())
        } else {
            throw e
        }
    }.mapLatest {
        it
    }

    override fun updateLevel(level: Int) {
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getIntPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL)] =
                        level
                }
                cachedMap?.set(
                    getIntPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL),
                    level
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun updateLastSeen() {
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getPreferences(PREFERENCE_KEY_PREFIX + LAST_SEEN)] =
                        System.currentTimeMillis().toString()
                }
                cachedMap?.set(
                    getPreferences(PREFERENCE_KEY_PREFIX + LAST_SEEN),
                    System.currentTimeMillis().toString()
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun updateLives(lives: Int, isFirstTime: Boolean) {
        externalScope.launch {
            val currentLives =
                cachedMap?.get(getIntPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES))
                    ?: MAX_LIVES
            var totalLives = when (currentLives) {
                3 -> currentLives
                2 -> currentLives + lives
                1 -> currentLives + lives
                else -> currentLives + lives
            }
            if (isFirstTime)
                totalLives = 3
            val max3lives = if (totalLives > MAX_LIVES) MAX_LIVES else totalLives
            try {
                dataStore.edit { preferences ->
                    preferences[getIntPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)] =
                        max3lives
                }
                cachedMap?.set(
                    getIntPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES),
                    max3lives
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun removeLives(lives: Int) {
        val currentLives = getLives() ?: 0
        val newLives = (currentLives - lives)
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getIntPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)] =
                        newLives
                }
                cachedMap?.set(
                    getIntPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES),
                    newLives
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun updateCredits(credit: Int, isFirstTime: Boolean) {
        externalScope.launch {
            try {
                val existingCredit = getCredit() ?: 0
                var totalCredit = existingCredit + credit
                if (isFirstTime)
                    totalCredit = credit

                dataStore.edit { preferences ->
                    preferences[getIntPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL)] =
                        totalCredit
                }
                cachedMap?.set(
                    getIntPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL),
                    totalCredit
                )
            } catch (_: Exception) {
            }
        }
    }

    override fun wipeData() {
        cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        val keys = preferences.asMap().keys.filter {
                            it.name.startsWith(PREFERENCE_KEY_PREFIX)
                        }
                        for (key in keys) {
                            preferences.remove(key)
                        }
                    }
                    cachedMap?.clear()
                } catch (_: Exception) {
                }
                setUpCacheMap()
            }
        }
    }

    private fun getPreferences(key: String) = stringPreferencesKey(key)
    private fun getIntPreferences(key: String) = intPreferencesKey(key)
    private fun getBoolPreferences(key: String) = booleanPreferencesKey(key)

    override fun setBoughtTapGame(value: Boolean) {
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getBoolPreferences(PREFERENCE_KEY_PREFIX + BOUGHT_TAP_GAME)] =
                        value
                }
                cachedMap?.set(
                    getBoolPreferences(PREFERENCE_KEY_PREFIX + BOUGHT_TAP_GAME),
                    value
                )
            } catch (_: Exception) {
            }
        }
    }
}
