package com.basta.guessemoji.data

import android.content.Context
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
        const val LAST_SEEN = "lastSeen"
        const val TODAY_REWARDS_LIST = "today_rewards_list"
    }
    private var cachedMap: MutablePreferences? = null
    private val Context.dataStore by preferencesDataStore(APP_DATA_STORE)
    private val dataStore : DataStore<Preferences> = context.dataStore

    init {
        setUpCacheMap()
    }

    private fun setUpCacheMap() {
        externalScope.launch {
            supervisorScope {
                try {
                    cachedMap = getUserInto().first().toMutablePreferences()
                } catch (e: Exception) {

                }
            }
        }
    }

    override fun getUser(): User {
        var level = "0"
        var credit = "0"
        externalScope.launch {
             credit =
                cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL)) ?: "0"
                   // credit

             level =
                 cachedMap?.get(getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL)) ?: "0"


        } // level

        return User(credit.toIntOrNull()?: 0, level.toIntOrNull() ?: 0)

    }

    private fun getUserInto() = dataStore.data.catch { e ->
        if (e is IOException){
            emit(emptyPreferences())
        } else {
            throw e
        }

    }.map { it }


    override fun addCredits(credit: Int) {
      //  cachedMap?.let{
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL)] = credit.toString()
                    }
                    cachedMap?.set(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL), credit.toString())
                } catch (e: Exception){}
            }
      //  }
    }

    override fun updateLevel(level: Int) {
      //  cachedMap?.let {
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences[getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL)] = level.toString()
                    }
                    cachedMap?.set(getPreferences(PREFERENCE_KEY_PREFIX + GAME_LEVEL), level.toString())
                } catch (e: Exception){}
            }
      //  }
    }

    override fun removeCredits(credit: Int) {
     //   cachedMap?.let{
            externalScope.launch {
                try {
                    dataStore.edit { preferences ->
                        preferences.remove(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL))
                    }
                    cachedMap?.remove(getPreferences(PREFERENCE_KEY_PREFIX + CREDITS_TOTAL))
                } catch (e: Exception) {}
            }
      //  }
    }

    override fun wipeData() {
       // cachedMap?.let {
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
                } catch (e: Exception) {}
        //    }
        }
        setUpCacheMap()
    }

    private fun getPreferences(key: String) = stringPreferencesKey(key)
}