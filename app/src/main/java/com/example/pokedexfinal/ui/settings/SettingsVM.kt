package com.example.pokedexfinal.ui.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.pokedexfinal.datamodel.UserPreferences
import com.example.pokedexfinal.dependencies.MyPokedex
import com.example.pokedexfinal.repositories.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsVM(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _uiState : MutableStateFlow<UserPreferences> = MutableStateFlow(UserPreferences())
    val uiState : StateFlow<UserPreferences> = _uiState.asStateFlow()

    init {
        //al iniciar toma el estado desde el DataStore.
        viewModelScope.launch {
            updateState()
        }
    }

    //actualiza el flujo de estado con los datos de DataStore.
    private suspend fun updateState() {
        userPreferencesRepository.getUserPrefs().collect { userPrefFlow ->
            _uiState.update { currentState ->
                userPrefFlow.copy()
            }
        }
    }

    fun saveSettings(name: String, checked : Boolean) {
        Log.d("datastore", "skipWelcome: $checked")
        viewModelScope.launch {
            //edita el DataStore.
            try {
                userPreferencesRepository.saveSettings(name,checked)
                updateStateSuccess()
            } catch (e: Error) {
                updateStateError()
            }

        }
    }

    private suspend fun updateStateSuccess() {
        _uiState.update { currentState ->
            currentState.copy(
                savedSkipWelcome = true
            )
        }
    }

    private suspend fun updateStateError() {
        _uiState.update { currentState ->
            currentState.copy(
                error = true
            )
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

                return SettingsVM(
                    (application as MyPokedex).appContainer.userPreferencesRepository
                ) as T
            }
        }
    }
}