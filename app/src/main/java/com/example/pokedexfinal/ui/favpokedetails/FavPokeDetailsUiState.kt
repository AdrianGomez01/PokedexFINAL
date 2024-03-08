package com.example.pokedexfinal.ui.favpokedetails

import com.example.pokedexfinal.data.Poke


data class FavPokeDetailsUiState(
    val isLoading : Boolean = true,
    val poke : Poke? = null
)
