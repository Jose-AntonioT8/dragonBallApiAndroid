package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.remote.model.CharacterListRemote
import com.example.dragonballapiandroid.data.remote.model.CharacterRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CharacterApi {
    @GET("/api/characters")
    suspend fun readAll(): Response<CharacterListRemote>
    @GET("/api/characters/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<CharacterRemote>
    @GET("/api/characters?name={name}")
    suspend fun readOne(@Path("name") name: String): Response<CharacterRemote>


}