package com.example.pokedexfinal.datamodel

data class UserPreferences (
    val name : String = "",
    val showWelcome : Boolean = true,

    ) {
    //valores constantes auxiliares para las preferencias de usuario.
    companion object {
        const val SETTINGS_FILE = "settings"
        const val USER_NAME = "username"
        const val ANONYMOUS = ""
    }
}