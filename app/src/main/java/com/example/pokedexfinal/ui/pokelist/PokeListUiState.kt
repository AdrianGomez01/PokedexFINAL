package com.example.pokedexfinal.ui.pokelist

import com.example.pokedexfinal.data.Poke

data class PokeListUiState {
    val isLoading: Boolean = true,
    val pokeList: List<Poke> = emptyList()
}