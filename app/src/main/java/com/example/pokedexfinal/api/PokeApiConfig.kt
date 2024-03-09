package com.example.pokedexfinal.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeApiConfig {
    companion object {

        const val BASE_URL = "https://pokeapi.co/api/v2/"

        //Definición de la api de Retrofit2.
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
