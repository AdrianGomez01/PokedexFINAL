package com.example.pokedexfinal.datamodel

data class UserPreferences (
    val name : String = "",
    val skipWelcome : Boolean = false,
    val savedSkipWelcome : Boolean = false,
    val error : Boolean = false,

    ) {
    //valores constantes auxiliares para las preferencias de usuario.
    companion object {
        const val SETTINGS_FILE = "settings"
        const val USER_NAME = "username"
        const val ANONYMOUS = ""
        const val SKIP = "skipWelcome"
        const val SKIP_DEFAULT = false
    }
}