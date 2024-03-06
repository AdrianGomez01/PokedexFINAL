package com.example.pokedexfinal.dependencies

interface Factory<T> {
    fun create() : T
}