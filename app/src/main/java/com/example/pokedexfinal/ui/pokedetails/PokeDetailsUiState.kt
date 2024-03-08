package com.example.pokedexfinal.ui.pokedetails

import com.example.pokedexfinal.api.Pokemon



data class PokeDetailsUiState(
    val isLoading : Boolean = true,
    val poke : Pokemon? = null
)
