package com.example.pokedexfinal.repositories

import android.util.Log
import com.example.pokedexfinal.api.ApiService
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.datasource.PokesDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import kotlin.random.Random

class FavPokemonsRepository (
    private val pokesDao: PokesDao,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAll() : List<Pokemon> = withContext(ioDispatcher) {
        return@withContext pokesDao.getAll()
    }

    suspend fun isFav(poke:Pokemon) = withContext(ioDispatcher){
        return@withContext pokesDao.isFav(poke.id)
    }

    suspend fun insertPoke(poke:Pokemon) = withContext(ioDispatcher){
        return@withContext pokesDao.insert(poke)
    }

    suspend fun deletePoke(poke:Pokemon) = withContext(ioDispatcher){
        return@withContext pokesDao.delete(poke)
    }


}