package com.example.pokedexfinal.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.pokedexfinal.api.Pokemon
import com.example.pokedexfinal.datamodel.UserComents

@Database(
    entities = [Pokemon::class, UserComents::class],
    version = 2,
    exportSchema = false)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun pokesDao(): PokesDao
    abstract fun comentsDao(): ComentsDao

    companion object {
        @Volatile
        private var Instance: LocalDatabase? = null

        fun getDatabase(context: Context): LocalDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, LocalDatabase::class.java, "fav_pokemons_database")
                    // Setting this option in your app's database builder means that Room
                    // permanently deletes all data from the tables in your database when it
                    // attempts to perform a migration with no defined migration path.
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}