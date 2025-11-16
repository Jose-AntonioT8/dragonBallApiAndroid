package com.example.dragonballapiandroid.data.remote
import com.example.dragonballapiandroid.data.model.Character
interface CharacterDataSource {

    suspend fun readAll():List<Character>
    suspend fun readOne(id:Long): Character?

    suspend fun raedPage(page:Int):List<Character>
}


