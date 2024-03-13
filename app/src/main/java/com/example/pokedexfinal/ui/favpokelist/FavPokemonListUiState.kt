package com.example.pokedexfinal.ui.favpokelist

import com.example.pokedexfinal.api.Pokemon

data class FavPokemonListUiState(
    val isLoading : Boolean = true,
    val pokeList: List<Pokemon> = emptyList()
)