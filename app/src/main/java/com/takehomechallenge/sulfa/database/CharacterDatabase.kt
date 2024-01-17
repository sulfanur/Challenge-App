package com.takehomechallenge.sulfa.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CharacterFavorite::class], version = 1)
abstract class CharacterDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterDao
    companion object {
        @Volatile
        private var INSTANCE: CharacterDatabase? = null
        @JvmStatic
        fun getDatabase(context: Context): CharacterDatabase {
            if (INSTANCE == null) {
                synchronized(CharacterDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        CharacterDatabase::class.java, "character_db")
                        .build()
                }
            }
            return INSTANCE as CharacterDatabase
        }
    }
}