package com.example.dragonballapiandroid.data.repository

import com.example.dragonballapiandroid.data.model.Planet
import kotlinx.coroutines.flow.Flow

interface PlanetRepository {
    suspend fun readAll(): Result<List<Planet>>
    suspend fun readOne(id:Long): Result<Planet>

    suspend fun readPage(page:Int): List<Planet>

    fun observe(): Flow<Result<List<Planet>>>
    suspend fun delete(id:Long)

    suspend fun refresh()

    suspend fun insert(planet: Planet)
}