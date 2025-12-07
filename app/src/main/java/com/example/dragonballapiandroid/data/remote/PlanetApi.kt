package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.remote.model.PlanetListRemote
import com.example.dragonballapiandroid.data.remote.model.PlanetRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlanetApi {
    @GET("/api/planets?limit=58")
    suspend fun readAll(): Response<PlanetListRemote>
    @GET("/api/planets/{id}")
    suspend fun readOne(@Path("id") id: Long): Response<PlanetRemote>
    @GET("/api/planets")
    suspend fun readOne(@Query("name") name: String): Response<List<PlanetRemote>>
    @GET("/api/planets")
    suspend fun readPage(@Query("page") page: Int): Response<PlanetListRemote>

    @GET("/api/planets/{id}")
    suspend fun delete(@Path("id") id: Long)

    suspend fun insert(planet: PlanetRemote)
}