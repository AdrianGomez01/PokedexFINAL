package com.example.pokedexfinal.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedexfinal.api.Pokemon

@Dao
interface PokesDao {
    @Query("SELECT * FROM pokemon")
    fun getAll(): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE id IN (:pokeIds)")
    fun loadAllByIds(pokeIds: IntArray): List<Pokemon>

    @Query("SELECT * FROM pokemon WHERE nombre LIKE :name LIMIT 1")
    fun findByName(name: String): Pokemon

    @Query("SELECT * FROM pokemon WHERE id LIKE :id")
    fun isFav(id: Int): Pokemon

    @Insert
    fun insert(pokes: Pokemon)

    @Delete
    fun delete(poke: Pokemon)
}