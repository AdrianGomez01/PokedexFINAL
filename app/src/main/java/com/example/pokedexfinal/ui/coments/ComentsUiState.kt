package com.example.pokedexfinal.ui.coments

import com.example.pokedexfinal.datamodel.UserComents

data class ComentsUiState(
    val isLoading : Boolean = true,
    val comentList: List<UserComents> = emptyList(),
    val autorName : String = "",
    var pokeId : Int = 0,
    val text : String = "",
)
