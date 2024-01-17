package com.takehomechallenge.sulfa.ui.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.sulfa.database.CharacterFavorite
import com.takehomechallenge.sulfa.repository.FavoriteRepository

class FavoriteViewModel(context: Context): ViewModel() {

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(context)
    val favUserList: LiveData<List<CharacterFavorite>> = favoriteRepository.getFavoriteList()
}