package com.example.pokedexfinal.datamodel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( "userComents" )
data class UserComents(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "autor")
    val autor: String = "",
    @ColumnInfo(name = "pokeId")
    val pokeId: Int,
    @ColumnInfo(name = "texto")
    val texto: String = "",
){
    companion object {
        const val POKE_ID = "pokeId"
        const val TEXTO = "texto"
        const val AUTOR = "autor"
    }
}

