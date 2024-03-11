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
            return@map UserPreferences(
                name = name,

                )
        }
    }

    //Escribe los datos en el Datastore.
    suspend fun saveSettings(name: String) {
        //edita el DataStore.
        userDataStore.edit { userPreferences ->
            userPreferences[stringPreferencesKey(UserPreferences.USER_NAME)] = name
        }
    }

}