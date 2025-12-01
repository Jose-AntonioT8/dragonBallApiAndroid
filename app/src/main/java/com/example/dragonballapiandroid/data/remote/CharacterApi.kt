package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.remote.model.CharacterListRemote
import com.example.dragonballapiandroid.data.remote.model.CharacterRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApi {
    @GET("/api/characters?limit=58")
    suspend fun readAll(): Response<CharacterListRemote>
    @GET("/api/characters/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<CharacterRemote>
    @GET("/api/characters")
    suspend fun readOne(@Query("name") name: String): Response<List<CharacterRemote>>
    @GET("/api/characters")
    suspend fun readPage(@Query("page") page: Int): Response<CharacterListRemote>

    @GET("/api/characters/{id}")
    suspend fun delete(@Path("id") id: Long)



}