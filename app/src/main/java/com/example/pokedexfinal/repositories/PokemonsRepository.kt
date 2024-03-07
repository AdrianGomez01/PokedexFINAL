package com.example.pokedexfinal.repositories

import com.example.pokedexfinal.api.ApiService
import com.example.pokedexfinal.data.Poke
import retrofit2.Response
import kotlin.random.Random

class PokemonsRepository(
    val pokeApiService: ApiService
) {

    companion object {
        const val NUM_HEROES = 731
    }


    suspend fun getPoke(id : Int) : Response<Poke> {
        var myPoke : Poke? = null
        var fullPokeResp = pokeApiService.getFullPokemon(id)
        if(fullPokeResp.isSuccessful) {
            val fullPoke = fullPokeResp.body()
            fullPoke?.let {
                myPoke = it.toPoke()
            }
            return Response.success(myPoke)
        } else
            return Response.error(null,null)
    }

    suspend fun getRandPoke() : Response<Poke> {
        var myPoke : Poke? = null
        val seed = System.currentTimeMillis()
        var x = (1..NUM_HEROES).random(Random(seed))
        return getPoke(x)
    }

    suspend fun getSomeRandHeroes(num : Int) : Response<List<Hero>> {
        var heroList : MutableList<Hero> = mutableListOf()
        for (i in 1 .. num) {
            val heroResp = getRandHero()
            if(heroResp.isSuccessful) {
                heroResp.body()?.let { heroList.add(heroList.size,heroResp.body()!!) }
            }
            else {
                return Response.error(null,null)
            }
        }
        return Response.success(heroList)
    }
}
