package com.example.pokedexfinal.api

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey val id : Int,
    @ColumnInfo(name = "nombre") val name: String = "",
    @ColumnInfo(name = "tipo1")val type1: String = "",
    @ColumnInfo(name = "tipo2")val type2: String = "",
    @ColumnInfo(name = "foto")val photo: String = "",
    @ColumnInfo(name = "peso")val peso: String = "",
    @ColumnInfo(name = "altura")val altura: String = "",
)
