package com.takehomechallenge.sulfa.api


import com.takehomechallenge.sulfa.api.response.CharacterResponse
import com.takehomechallenge.sulfa.api.response.ResultsItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {
    @GET("character/")
    fun getCharacter(
        @Query("q") q: String?):
            Call<CharacterResponse>
    @GET("character/{id}")
    fun getDetailUser(
        @Path("id") id: String?):
            Call<ResultsItem>
}