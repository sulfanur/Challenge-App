package com.takehomechallenge.sulfa.ui.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.sulfa.api.ApiConfig
import com.takehomechallenge.sulfa.api.response.ResultsItem
import com.takehomechallenge.sulfa.database.CharacterFavorite
import com.takehomechallenge.sulfa.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel (private val context: Context): ViewModel(){

    private var _character = MutableLiveData<ResultsItem>()
    val character: LiveData<ResultsItem> = _character

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(context)
    val favoriteCharacter : LiveData<List<CharacterFavorite>> = favoriteRepository.getFavoriteList()


    fun getDataUser(id: String) {
        if (character.value == null) {
            _isLoading.value = true
            val client = ApiConfig.getApiService().getDetailUser(id)
            client.enqueue(object : Callback<ResultsItem> {
                override fun onResponse(
                    call: Call<ResultsItem>,
                    response: Response<ResultsItem>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        _character.value = response.body()
                        Log.d("TAG Done", "onResponse: ${response.body()}")
                    } else {
                        Log.e("TAG False", "onFailureResponse: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResultsItem>, t: Throwable) {
                    _isLoading.value = false
                    Log.e("TAG", "onFailure: ${t.message}")
                }
            })
        }
    }

    fun insertFavoriteUser(character: ResultsItem) {
        val data = CharacterFavorite(character.id, character.name, character.species, character.gender, character.origin?.name, character.location?.name,character.image)
        favoriteRepository.insertFavorite(data)
        Toast.makeText(context, "Success Add to Favorite", Toast.LENGTH_SHORT).show()
    }

    fun deleteFavoriteUser(data: CharacterFavorite) {
        favoriteRepository.deleteFavorite(data)
        Toast.makeText(context, "Success Delete from Favorite", Toast.LENGTH_SHORT).show()
    }
}