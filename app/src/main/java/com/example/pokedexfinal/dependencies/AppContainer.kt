package com.example.pokedexfinal.dependencies

import android.content.Context
import com.example.pokedexfinal.api.ApiService
import com.example.pokedexfinal.api.PokeApiConfig
import com.example.pokedexfinal.repositories.PokemonsRepository

class AppContainer(context : Context) {

    val pokemonsRepository: PokemonsRepository
        get() {
            return this.pokemonsRepository
        }

    //Creación del servicio, usando la api.
    private val pokeApiService = PokeApiConfig.provideRetrofit().create(ApiService::class.java)

    //Creación del repositorio que hará uso de la API.
    val pokemonRepository : PokemonsRepository = PokemonsRepository(pokeApiService)


}