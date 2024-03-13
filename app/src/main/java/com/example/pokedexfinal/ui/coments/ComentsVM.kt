package com.example.pokedexfinal.ui.coments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.datamodel.UserComents
import com.example.pokedexfinal.datamodel.UserPreferences
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.ComentsRepository
import com.example.pokedexfinal.ui.pokelist.PokeListUiState
import com.example.pokedexfinal.ui.pokelist.PokeListVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Error

class ComentsVM(
    private val comentsRepository: ComentsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ComentsUiState> = MutableStateFlow(ComentsUiState())
    val uiState: StateFlow<ComentsUiState> = _uiState.asStateFlow()

    init {
        getComents()
    }

    private fun getComents() {
        viewModelScope.launch {
            val comentList = comentsRepository.getPokeComents(uiState.value.pokeId)
            _uiState.update { currentSate ->
                currentSate.copy(
                    isLoading = false,
                    comentList = comentList,
                )
            }
        }
    }


    fun addComent(coment: UserComents) {
        viewModelScope.launch {
            _uiState.update { currentState ->
                comentsRepository.insertComent(coment)
                currentState.copy(
                    autorName = coment.autor,
                    pokeId = coment.pokeId,
                    text = coment.texto
                )
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
                val application =
                    checkNotNull(extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY])

                return ComentsVM(
                    (application as MyPokedex).appContainer.comentsRepository
                ) as T
            }
        }
    }
}