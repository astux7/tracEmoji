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
import com.basta.guessemoji.domain.model.User
import com.basta.guessemoji.domain.repository.UserPreferenceRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class UserPreferenceRepositoryImp(
    context: Context,
    private val externalScope: CoroutineScope
) : UserPreferenceRepository {
    companion object {
        const val PREFERENCE_KEY_PREFIX = "pref_user_"
        const val APP_DATA_STORE = "tracemoji"
        const val CREDITS_TOTAL = "credits_total"
        const val GAME_LEVEL = "game_level"
        const val USER_LIVES = "user_lives"
        const val LAST_SEEN = "lastSeen"
        const val TODAY_REWARDS_LIST = "today_rewards_list"
    }

    private var cachedMap: MutablePreferences? = null
    private val Context.dataStore by preferencesDataStore(APP_DATA_STORE)
    private val dataStore: DataStore<Preferences> = context.dataStore

    init {
        setUpCacheMap()
    }

    private fun setUpCacheMap() {
        externalScope.launch {
            supervisorScope {
                try {
                    cachedMap = getUserInto().first().toMutablePreferences()
                } catch (_: Exception) {
                }
            }
        }
    }

    private fun getCredit() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL)) ?: "15"

    private fun getLevel() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL)) ?: "0"

    private fun getLastSeen() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + LAST_SEEN))
            ?: System.currentTimeMillis().toString()

    private fun getLives() =
        cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)) ?: "3"


    override fun getUser(): User {
        val level: String = getLevel()
        val credit: String = getCredit()
        val lives: String = getLives()
        val lastSeen: String = getLastSeen()

        Log.d("getUser", "Credit: $credit, Level: $level, lives: $lives , last seen : $lastSeen")

        return User(
            credit.toIntOrNull() ?: 0,
            level.toIntOrNull() ?: 0,
            lives.toIntOrNull() ?: 0,
            lastSeen.toLongOrNull() ?: 0
        )
    }

    private fun getUserInto() = dataStore.data.catch { e ->
        if (e is IOException) {
            emit(emptyPreferences())
        } else {
            throw e
        }

    }.map { it }


    override fun addCredits(credit: Int) {
        cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL)] =
                            credit.toString()
                    }
                    cachedMap?.set(
                        getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL),
                        credit.toString()
                    )
                } catch (_: Exception) {
                }
            }
        }
    }

    override fun updateLevel(level: Int) {
        cachedMap?.let {
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
                }
            }
        }
    }

    override fun updateLastSeen() {
        cachedMap?.let {
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
                }
            }
        }
    }

    override fun updateLives(lives: Int) {
        cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)] =
                            lives.toString()
                    }
                    cachedMap?.set(
                        getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES),
                        lives.toString()
                    )
                } catch (_: Exception) {
                }
            }
        }
    }

    override fun removeLives(lives: Int) {
        val currentLives = getLives()
        val newLives = ((currentLives.toIntOrNull() ?: 0) - lives).toString()
        cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        val currentLives = getLives()
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES)] =
                            newLives
                    }
                    cachedMap?.set(
                        getPreferences(PREFERENCE_KEY_PREFIX + USER_LIVES),
                        newLives
                    )
                } catch (_: Exception) {
                }
            }
        }
    }

    override fun updateCredits(credit: Int) {
        cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences.remove(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL))
                    }
                    cachedMap?.remove(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL))
                } catch (_: Exception) {
                }
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
            }
        }
        setUpCacheMap()
    }

    private fun getPreferences(key: String) = stringPreferencesKey(key)
}