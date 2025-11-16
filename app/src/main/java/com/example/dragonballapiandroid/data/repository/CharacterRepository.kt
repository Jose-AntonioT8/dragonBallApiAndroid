package com.example.dragonballapiandroid.data.repository
import com.example.dragonballapiandroid.data.model.Character
interface CharacterRepository {
    suspend fun readAll(): List<Character>
    suspend fun readOne(id:Long): Character?

    suspend fun readPage(page:Int): List<Character>
}


