package com.example.pokedexfinal

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

class MainActivity : AppCompatActivity() {
    val Context.dataStore : DataStore<Preferences> by preferencesDataStore("settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun createMail() {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "plain/text"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(getString(R.string.mail)))
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.subject))
        startActivity(Intent.createChooser(intent, ""))
    }
}