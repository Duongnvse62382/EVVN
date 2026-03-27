package com.king.evvn.data.source.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
class SessionManager @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    companion object {
        private val KEY_TOKEN = stringPreferencesKey("token")
        private val KEY_LOGIN_TIME = longPreferencesKey("login_time")

        private const val SESSION_DURATION = 60 * 60 * 1000L
    }

    suspend fun saveSession(token: String) {
        dataStore.edit {
            it[KEY_TOKEN] = token
            it[KEY_LOGIN_TIME] = System.currentTimeMillis()
        }
    }

    fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { pref ->
            val token = pref[KEY_TOKEN]
            val loginTime = pref[KEY_LOGIN_TIME]

            if (token == null || loginTime == null) return@map false

            val current = System.currentTimeMillis()
            current - loginTime < SESSION_DURATION
        }
    }

    suspend fun clearSession() {
       dataStore.edit { it.clear() }
    }
}