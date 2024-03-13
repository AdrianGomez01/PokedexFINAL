package com.example.pokedexfinal.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.pokedexfinal.datamodel.UserComents

@Dao
interface ComentsDao {

    @Query("SELECT * FROM userComents")
    fun getAll(): List<UserComents>

    @Insert
    fun insert(coment: UserComents)

    @Query("SELECT * FROM userComents WHERE pokeId LIKE :id ")
    fun getPokeComents(id : Int):List<UserComents>
}