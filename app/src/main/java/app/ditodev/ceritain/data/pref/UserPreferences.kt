package app.ditodev.ceritain.data.pref

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import app.ditodev.ceritain.data.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    suspend fun saveSession(user: UserModel) {
        dataStore.edit {
            it[USER_ID] = user.userId
            it[NAME_KEY] = user.name
            it[TOKEN_KEY] = user.token
        }
    }

    fun getSession(): Flow<UserModel> {
        return dataStore.data.map {
            UserModel(
                it[USER_ID] ?: "",
                it[NAME_KEY] ?: "",
                it[TOKEN_KEY] ?: ""
            )
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    fun getToken(): Flow<String> {
        return dataStore.data.map {
            it[TOKEN_KEY] ?: ""
        }
    }

    companion object {
        private val USER_ID = stringPreferencesKey("userId")
        private val NAME_KEY = stringPreferencesKey("name")
        private val TOKEN_KEY = stringPreferencesKey("token")

        @Volatile
        private var instance: UserPreferences? = null
        fun getInstance(dataStore: DataStore<Preferences>) =
            instance ?: synchronized(this) {
                instance ?: UserPreferences(dataStore)
            }.also { instance = it }
    }
}