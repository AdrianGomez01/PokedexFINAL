package com.example.pokedexfinal.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("pokemon/{id}")
    suspend fun getFullPokemon(@Path("id") id : Int) : Response<PokeApiResponse>

}