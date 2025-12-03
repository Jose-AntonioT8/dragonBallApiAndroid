package com.example.dragonballapiandroid.data.repository
import com.example.dragonballapiandroid.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {
    suspend fun readAll(): Result<List<Character>>
    suspend fun readOne(id:Long): Result<Character>

    suspend fun readPage(page:Int): List<Character>

    fun observe(): Flow<Result<List<Character>>>
    suspend fun delete(id:Long)

    suspend fun refresh()
}


