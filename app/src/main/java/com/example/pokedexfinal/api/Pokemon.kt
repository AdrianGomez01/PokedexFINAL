package com.example.pokedexfinal.api

data class Pokemon(
    val id : Int,
    val name: String = "",
    val type1: String = "",
    val type2: String = "",
    val photo: String = "",
    val peso: String = "",
    val altura: String = "",
)
