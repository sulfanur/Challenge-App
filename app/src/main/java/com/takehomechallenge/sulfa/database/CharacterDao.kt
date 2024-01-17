package com.takehomechallenge.sulfa.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CharacterDao {

    @Query("SELECT * FROM favorite ORDER BY name ASC")
    fun getFavorite(): LiveData<List<CharacterFavorite>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertFavorite(favorite: CharacterFavorite)

    @Delete
    fun deleteFavorite(favorite: CharacterFavorite)
}