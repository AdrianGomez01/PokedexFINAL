package com.example.pokedexfinal.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("pokemon/{id}")
    suspend fun getFullPokemon(@Path("id") id : Int) : Response<PokeApiResponse>

}