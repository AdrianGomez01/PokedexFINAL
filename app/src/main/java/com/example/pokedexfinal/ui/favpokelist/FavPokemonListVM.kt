package com.example.pokedexfinal.ui.favpokelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.FavPokemonsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Error


class FavPokemonListVM(
    private val favPokemonsRepository: FavPokemonsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<FavPokemonListUiState> =
        MutableStateFlow(FavPokemonListUiState())
    val uiState: StateFlow<FavPokemonListUiState> = _uiState.asStateFlow()

    init {
        getPokes()
    }

    private fun getPokes() {
        viewModelScope.launch {
            val myFavPokeList = favPokemonsRepository.getAll()
            _uiState.update { currentSate ->
                currentSate.copy(
                    isLoading = false,
                    pokeList = myFavPokeList
                )
            }
        }
    }

    fun delFavPoke(poke: Pokemon) {
        try {
            viewModelScope.launch {
                _uiState.update { currentState ->
                    val delPokeList = currentState.pokeList.toMutableList()
                    delPokeList.remove(poke)
                    currentState.copy(
                        pokeList = delPokeList
                    )
                }
                favPokemonsRepository.deletePoke(poke)
            }
        } catch (e: Error) {
            //Error al eliminar el poke
        }

    }

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return FavPokemonListVM(
                    (application as MyPokedex).appContainer.favPokemonsRepository
                ) as T
            }
        }
    }
}
