package com.basta.guessemoji.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.basta.guessemoji.common.Constants.INITIAL_CREDITS
import com.basta.guessemoji.common.Constants.INITIAL_LEVEL
import com.basta.guessemoji.common.Constants.MAX_LIVES
import com.basta.guessemoji.domain.model.User
import com.basta.guessemoji.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class UserPreferenceRepositoryImp(
    context: Context,
    private val externalScope: CoroutineScope
) : UserPreferenceRepository {
    companion object {
        const val PREFERENCE_KEY_PREFIX = "pref_user_"
        const val APP_DATA_STORE = "bla"
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

    private val _userState = MutableStateFlow<User?>(null)
    val userState: StateFlow<User?> = _userState

    override suspend fun initialize() {
        setUpCacheMap()
        if (isFirstInstall() == null) {
            Log.d("kkkk", "FIRST_INSTALL - SET UP ")
            setFirstInstall()
            if (getLives() == null) {
                updateLives(MAX_LIVES, true)
            }
            if (getLevel() == null) {
                updateLevel(INITIAL_LEVEL)
            }
            if (getCredit() == null) {
                updateCredits(INITIAL_CREDITS, true)
            }
            if (getBoughtTapGame() == null) {
                setBoughtTapGame("false")
            }


        } else {
            Log.d("kkkk", "NOT FIRST INSTALL - SET UP ")
            getUser()
        }

    }

    private fun isFirstInstall() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + FIRST_INSTALL)) ?: null

    private fun setFirstInstall() {
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getPreferences(PREFERENCE_KEY_PREFIX + FIRST_INSTALL)] =
                        "false"
                }
                cachedMap?.set(
                    getPreferences(PREFERENCE_KEY_PREFIX + FIRST_INSTALL),
                    "false"
                )
            } catch (_: Exception) {
                Log.d("kkkk", "FAIl - FIRST_INSTALL")
            }
        }
    }

    private suspend fun setUpCacheMap() {
        externalScope.launch {
            supervisorScope {
                try {
                    cachedMap = getUserInto().first().toMutablePreferences()
                } catch (_: Exception) {
                    Log.d("kkkk", "FAIl - setUpCacheMap")

                }
            }
        }
    }

    private fun getCredit() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL))

    private fun getLevel() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL))

    private fun getLastSeen() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + LAST_SEEN))
            ?: System.currentTimeMillis().toString()

    private fun getLives() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES))

    private fun getBoughtTapGame() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + BOUGHT_TAP_GAME))

    override fun getUser(): User {
        val level: String = getLevel() ?: 0.toString()
        val credit: String = getCredit() ?: 0.toString()
        val lives: String = getLives() ?: 0.toString()
        val lastSeen: String = getLastSeen()
        val boughtTapGame: Boolean = (getBoughtTapGame() == "true")

        Log.d(
            "kkkk",
            "Credit: $credit, level: $level, lives: $lives , last seen : $lastSeen , bought tap game: $boughtTapGame"
        )

        return User(
            credit = credit.toIntOrNull() ?: 0,
            level = level.toIntOrNull() ?: 0,
            lives = lives.toIntOrNull() ?: 0,
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
                    preferences[getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL)] =
                        level.toString()
                }
                cachedMap?.set(
                    getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL),
                    level.toString()
                )
            } catch (_: Exception) {
                Log.d("kkkk", "FAIl - updateLastSeen")
            }
        }
        getUser()
    }

    override fun updateLastSeen() {
        //   cachedMap?.let {
        externalScope.launch {
            try {
                dataStore.edit { preferences ->
                    preferences[getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL)] =
                        System.currentTimeMillis().toString()
                }
                cachedMap?.set(
                    getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL),
                    System.currentTimeMillis().toString()
                )
            } catch (_: Exception) {
                Log.d("kkkk", "FAIl - updateLastSeen")
            }
        }
        getUser()
        //  }
    }

    override fun updateLives(lives: Int, isFirstTime: Boolean) {
        //  cachedMap?.let {
        externalScope.launch {
            val currentLives =
                cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES))?.toInt()
                    ?: MAX_LIVES
            var totalLives = when (currentLives.toInt()) {
                3 -> currentLives.toInt()
                2 -> currentLives.toInt() + lives.toInt()
                1 -> currentLives.toInt() + lives.toInt()
                else -> currentLives.toInt() + lives.toInt()
            }
            if (isFirstTime)
                totalLives = 3
            val max3lives = if (totalLives > MAX_LIVES) MAX_LIVES.toInt() else totalLives.toInt()
            try {
                dataStore.edit { preferences ->
                    preferences[getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)] =
                        max3lives.toString()
                }
                cachedMap?.set(
                    getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES),
                    max3lives.toString()
                )
            } catch (_: Exception) {
                Log.d("kkkk", "FAIl - updateLives")
            }
        }
        getUser()
        //   }
    }

    override fun removeLives(lives: Int) {
        val currentLives = getLives()?.toIntOrNull() ?: 0
        val newLives = (currentLives - lives).toString()
     //   cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)] =
                            newLives
                    }
                    cachedMap?.set(
                        getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES),
                        newLives
                    )
                } catch (_: Exception) {
                    Log.d("kkkk", "FAIl - removeLives")
                }
            }
    //    }
        getUser()
    }

    override fun updateCredits(credit: Int, isFirstTime: Boolean) {
        //  cachedMap?.let {
        externalScope.launch {
            try {
                val existingCredit = getCredit()?.toIntOrNull() ?: 0
                var totalCredit = existingCredit + credit
                if (isFirstTime)
                    totalCredit = credit
                dataStore.edit { preferences ->
                    preferences[getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL)] =
                        totalCredit.toString()
                }
                cachedMap?.set(
                    getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL),
                    totalCredit.toString()
                )
            } catch (_: Exception) {
                Log.d("kkkk", "FAIl - updateCredits")
            }
            //   }
        }
        getUser()
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
                    Log.d("kkkk", "FAIl")
                }
                setUpCacheMap()
            }
        }

    }

    private fun getPreferences(key: String) = stringPreferencesKey(key)

    override fun setBoughtTapGame(value: String) {

            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + BOUGHT_TAP_GAME)] =
                            value
                    }
                    cachedMap?.set(
                        getPreferences(PREFERENCE_KEY_PREFIX + BOUGHT_TAP_GAME),
                        value
                    )
                } catch (_: Exception) {
                    Log.d("kkkk", "FAIl - setBoughtTapGame")
                }
                getUser()

        }
    }
}
