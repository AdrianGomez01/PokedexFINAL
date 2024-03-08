package com.example.pokedexfinal.repositories

import com.example.pokedexfinal.api.ApiService
import com.example.pokedexfinal.api.Pokemon
import retrofit2.Response
import kotlin.random.Random

class PokemonsRepository(
    val pokeApiService: ApiService
) {

    companion object {
        const val NUM_POKES = 151
    }


    suspend fun getPoke(id : Int) : Response<Pokemon> {
        var myPoke : Pokemon? = null
        var fullPokeResp = pokeApiService.getFullPokemon(id)
        if(fullPokeResp.isSuccessful) {
            val fullPoke = fullPokeResp.body()
            fullPoke?.let {
                myPoke = it.toPokemon()
            }
            return Response.success(myPoke)
        } else
            return Response.error(null,null)
    }

    suspend fun getRandPoke() : Response<Pokemon> {
        var myPoke : Pokemon? = null
        val seed = System.currentTimeMillis()
        var x = (1..NUM_POKES).random(Random(seed))
        return getPoke(x)
    }

    suspend fun getSomeRandPokes(num : Int) : Response<List<Pokemon>> {
        var pokeList : MutableList<Pokemon> = mutableListOf()
        for (i in 1 .. num) {
            val pokeResp = getRandPoke()
            if(pokeResp.isSuccessful) {
                pokeResp.body()?.let { pokeList.add(pokeList.size,pokeResp.body()!!) }
            }
            else {
                return Response.error(null,null)
            }
        }
        return Response.success(pokeList)
    }
}
