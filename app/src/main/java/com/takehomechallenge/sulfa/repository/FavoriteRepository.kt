package com.takehomechallenge.sulfa.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.takehomechallenge.sulfa.database.CharacterDao
import com.takehomechallenge.sulfa.database.CharacterDatabase
import com.takehomechallenge.sulfa.database.CharacterFavorite
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository (context: Context){
    private var characterDao: CharacterDao
    private var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = CharacterDatabase.getDatabase(context)
        characterDao = db.characterDao()
    }

    fun getFavoriteList(): LiveData<List<CharacterFavorite>> = characterDao.getFavorite()

    fun insertFavorite(characteFavoriter: CharacterFavorite) {
        executorService.execute { characterDao.insertFavorite(characteFavoriter)
        }
    }
    fun deleteFavorite(characteFavoriter: CharacterFavorite) {
        executorService.execute {
            characterDao.deleteFavorite(characteFavoriter)
        }
    }

}