package com.example.dragonballapiandroid.data

import com.example.dragonballapiandroid.data.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetDataSource {
    suspend fun addAll(planetList: List<Planet>)
    fun observe(): Flow<Result<List<Planet>>>
    suspend fun readAll(): Result<List<Planet>>
    suspend fun readOne(id: Long): Result<Planet>
    suspend fun isError()
    suspend fun insert(planet: Planet)
    suspend fun raedPage(page:Int):List<Planet>

    suspend fun delete(id:Long)

}