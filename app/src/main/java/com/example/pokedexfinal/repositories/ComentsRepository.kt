package com.example.pokedexfinal.repositories

import com.example.pokedexfinal.datamodel.UserComents
import com.example.pokedexfinal.datasource.ComentsDao
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ComentsRepository(
    private val comentsDao: ComentsDao,
    private val ioDispatcher : CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getAll() : List<UserComents> = withContext(ioDispatcher) {
        return@withContext comentsDao.getAll()
    }

    suspend fun insertComent(coment:UserComents) = withContext(ioDispatcher){
        return@withContext comentsDao.insert(coment)
    }

    suspend fun getPokeComents(id:Int) : List<UserComents> = withContext(ioDispatcher) {
        return@withContext comentsDao.getPokeComents(id)
    }
}