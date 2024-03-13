package com.example.pokedexfinal.ui.pokelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.FavPokemonsRepository
import com.example.pokedexfinal.repositories.PokemonsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PokeListVM(
    private val pokemonsRepository: PokemonsRepository,
    private val favPokemonsRepository: FavPokemonsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<PokeListUiState> = MutableStateFlow(PokeListUiState())
    val uiState: StateFlow<PokeListUiState> = _uiState.asStateFlow()

    init {
        getSomeRandPokes(NUM_POKEMONS)
    }

    private fun getSomeRandPokes(numPokes: Int) {
        viewModelScope.launch {
            val myPokeResp = pokemonsRepository.getSomeRandPokes(NUM_POKEMONS)
            if (myPokeResp.isSuccessful) {
                val myPokes = myPokeResp.body()
                _uiState.update { currentSate ->
                    currentSate.copy(
                        isLoading = false,
                        pokeList = myPokes?.let { it.toList() } ?: emptyList<Pokemon>()
                    )
                }
            } else {
                //error en la respuesta...
            }
        }
    }

    fun saveFavPoke(poke: Pokemon) {
        viewModelScope.launch {
            val isFav = isFaved(poke)
            if (!isFav) {
                favPokemonsRepository.insertPoke(poke)
                _uiState.update {
                    it.copy(
                        isFav = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        isFav = true
                    )
                }
            }
        }
    }


    suspend fun isFaved(poke: Pokemon): Boolean {
        return withContext(Dispatchers.IO) {
            return@withContext favPokemonsRepository.isFav(poke) != null

        }
    }

    companion object {

        const val NUM_POKEMONS = 10

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                // Get the Application object from extras
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return PokeListVM(
                    (application as MyPokedex).appContainer.pokemonRepository,
                    (application as MyPokedex).appContainer.favPokemonsRepository,
                ) as T
            }
        }
    }
}