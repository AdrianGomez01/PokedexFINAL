package com.example.pokedexfinal.ui.pokedetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.PokemonsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PokeDetailsVM(
    private val pokemonsRepository: PokemonsRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<PokeDetailsUiState> = MutableStateFlow(
        PokeDetailsUiState()
    )
    val uiState : StateFlow<PokeDetailsUiState> = _uiState.asStateFlow()

    init {
    }

    fun setPoke(idPoke: String) {
        viewModelScope.launch {
            val pokeResp = pokemonsRepository.getPoke(idPoke.toInt())
            if(pokeResp.isSuccessful) {
                val poke = pokeResp.body()
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        poke = poke
                    )
                }
            } else {
                //procesar error...
            }
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
                val application = checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return PokeDetailsVM(
                    (application as MyPokedex).appContainer.pokemonsRepository
                ) as T
            }
        }
    }
}