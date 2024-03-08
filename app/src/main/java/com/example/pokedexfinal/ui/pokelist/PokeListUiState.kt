package com.example.pokedexfinal.ui.pokelist

import com.example.pokedexfinal.api.Pokemon

data class PokeListUiState(
    val isLoading : Boolean = true,
    val pokeList: List<Pokemon> = emptyList()
)
