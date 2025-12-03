package com.example.dragonballapiandroid.data

import com.example.dragonballapiandroid.data.model.Character
import kotlinx.coroutines.flow.Flow

interface CharacterDataSource {

    suspend fun addAll(characterList: List<Character>)
    fun observe(): Flow<Result<List<Character>>>
    suspend fun readAll(): Result<List<Character>>
    suspend fun readOne(id: Long): Result<Character>
    suspend fun isError()
    suspend fun insert(character: Character)
    suspend fun raedPage(page:Int):List<Character>

    suspend fun delete(id:Long)
}