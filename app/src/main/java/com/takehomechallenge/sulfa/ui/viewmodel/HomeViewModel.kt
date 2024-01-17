package com.takehomechallenge.sulfa.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.takehomechallenge.sulfa.api.ApiConfig
import com.takehomechallenge.sulfa.api.response.CharacterResponse
import com.takehomechallenge.sulfa.api.response.ResultsItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _listUser = MutableLiveData<ArrayList<ResultsItem>>()
    val listUser: LiveData<ArrayList<ResultsItem>> = _listUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getUser()
    }

    private fun getUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getCharacter("")
        client.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _listUser.value = responseBody.results as ArrayList<ResultsItem>
                    }
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })
    }

    fun getDataUser(name: String) {
        _isLoading.value = true

        val client = ApiConfig.getApiService().getCharacter(name)
        client.enqueue(object : Callback<CharacterResponse> {
            override fun onResponse(call: Call<CharacterResponse>, response: Response<CharacterResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listUser.value = response.body()?.results as ArrayList<ResultsItem>
                } else {
                    Log.e("TAG", "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<CharacterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("TAG", "onFailure: ${t.message}")
            }
        })

    }
}