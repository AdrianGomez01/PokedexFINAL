package com.example.pokedexfinal.ui.pokedetails

import com.example.pokedexfinal.data.Poke


data class PokeDetailsUiState(
    val isLoading : Boolean = true,
    val poke : Poke? = null
)
