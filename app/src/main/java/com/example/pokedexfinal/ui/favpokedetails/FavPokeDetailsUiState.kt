package com.example.pokedexfinal.ui.favpokedetails

import com.example.pokedexfinal.api.Pokemon


data class FavPokeDetailsUiState(
    val isLoading : Boolean = true,
    val poke : Pokemon? = null
)
