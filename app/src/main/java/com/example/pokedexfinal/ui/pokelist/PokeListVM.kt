package com.example.pokedexfinal.ui.pokelist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.data.Poke
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.PokemonsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response

class PokeListVM(
    private val pokemonsRepository: PokemonsRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<PokeListUiState> = MutableStateFlow(PokeListUiState())
    val uiState : StateFlow<PokeListUiState> = _uiState.asStateFlow()

    init {
        getSomeRandPokes(NUM_POKEMONS)
    }
    private fun getSomeRandPokes(numPokes: Int) {
        viewModelScope.launch {
            val myPokeResp = pokemonsRepository.getSomeRandPokes(NUM_POKEMONS)
            if(myPokeResp.isSuccessful) {
                val myPokes = myPokeResp.body()
                _uiState.update { currentSate ->
                    currentSate.copy(
                        isLoading = false,
                        pokeList = myPokes?.let { it.toList() } ?: emptyList<Poke>()
                    )
                }
            } else {
                //error en la respuesta...
            }
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
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return PokeListVM(
                    (application as MyPokedex).appContainer.pokemonsRepository
                ) as T
            }
        }
    }
}