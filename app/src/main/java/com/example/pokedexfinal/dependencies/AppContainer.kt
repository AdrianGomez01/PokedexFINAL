package com.example.pokedexfinal.dependencies

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import com.example.pokedexfinal.api.ApiService
import com.example.pokedexfinal.api.PokeApiConfig
import com.example.pokedexfinal.datamodel.UserPreferences
import com.example.pokedexfinal.datasource.LocalDatabase
import com.example.pokedexfinal.repositories.FavPokemonsRepository
import com.example.pokedexfinal.repositories.PokemonsRepository
import com.example.pokedexfinal.repositories.UserPreferencesRepository

//Datastore. Configuración básica de la app.
val Context.userDataStore by preferencesDataStore(name = UserPreferences.SETTINGS_FILE)

class AppContainer(context: Context) {


    //Creación del servicio, usando la api.
    private val pokeApiService = PokeApiConfig.provideRetrofit().create(ApiService::class.java)

    //Creación del repositorio que hará uso de la API.
    val pokemonRepository: PokemonsRepository = PokemonsRepository(pokeApiService)


    //Repositorio de configuración de usuario.
    private val _userPreferencesRepository: UserPreferencesRepository by lazy {
        UserPreferencesRepository(context.userDataStore)
    }
    val userPreferencesRepository get() = _userPreferencesRepository


    //Repositorio de Pokemon Favoritos
    private val _favPokemonsRepository : FavPokemonsRepository by lazy {
        FavPokemonsRepository(LocalDatabase.getDatabase(context).pokesDao())
    }
    val favPokemonsRepository get() = _favPokemonsRepository


}