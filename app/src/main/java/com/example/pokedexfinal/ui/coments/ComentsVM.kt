package com.example.pokedexfinal.ui.coments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.datamodel.UserComents
import com.example.pokedexfinal.datasource.ComentsDao
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.ComentsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ComentsVM(
    private val comentsRepository: ComentsRepository
) : ViewModel() {

    private val _uiState: MutableStateFlow<ComentsUiState> = MutableStateFlow(ComentsUiState())
    val uiState: StateFlow<ComentsUiState> = _uiState.asStateFlow()



    init {

    }

    fun getComents( id : Int) {
        viewModelScope.launch {
            //TODO Tiene que ser aqui donde me falla al filtrar los coment por pokeID
           val comentList = comentsRepository.getPokeComents(id)
            //val comentList = comentsRepository.getAll()
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
                    text = coment.texto,
                )
            }
            getComents(coment.pokeId)
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