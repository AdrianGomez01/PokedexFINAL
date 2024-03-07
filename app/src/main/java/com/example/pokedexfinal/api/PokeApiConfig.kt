package com.example.pokedexfinal.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PokeApiConfig {
    companion object {
        //TODO
        as
        const val USER_TOKEN = ""
        const val BASE_URL = ""

        //Definición de la api de Retrofit2.
        fun provideRetrofit(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}
