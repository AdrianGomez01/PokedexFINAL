package com.example.pokedexfinal.repositories

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.pokedexfinal.datamodel.UserPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserPreferencesRepository(
    private val userDataStore: DataStore<Preferences>
) {

    //toma los datos de DataStore. Devuelve un flujo.
    fun getUserPrefs(): Flow<UserPreferences> {
        return userDataStore.data.map { userPreferences ->
            val name = userPreferences[stringPreferencesKey(UserPreferences.USER_NAME)]
                ?: UserPreferences.ANONYMOUS
            val checked = userPreferences[booleanPreferencesKey(UserPreferences.SKIP)]
                ?: UserPreferences.SKIP_DEFAULT
            return@map UserPreferences(
                name = name,
                skipWelcome = checked
                )
        }
    }

    //Escribe los datos en el Datastore.
    suspend fun saveSettings(name: String , checked: Boolean) {
        //edita el DataStore.
        userDataStore.edit { userPreferences ->
            userPreferences[stringPreferencesKey(UserPreferences.USER_NAME)] = name
            userPreferences[booleanPreferencesKey(UserPreferences.SKIP)] = checked
        }
    }

    suspend fun saveName(name: String) {
        //edita el DataStore.
        userDataStore.edit { userPreferences ->
            userPreferences[stringPreferencesKey(UserPreferences.USER_NAME)] = name
        }
    }


    suspend fun saveSettingsWelcome(checked: Boolean) {
        //edita el DataStore.
        userDataStore.edit { userPreferences ->
            userPreferences[booleanPreferencesKey(UserPreferences.SKIP)] = checked
        }
    }

}