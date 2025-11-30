package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.CharacterDataSource
import com.example.dragonballapiandroid.data.model.Character
import com.example.dragonballapiandroid.data.remote.model.CharacterRemote
import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val api: CharacterApi
) : CharacterDataSource {

    override suspend fun readAll(): List<Character> {
        val response = api.readAll()
        val finalist = mutableListOf<Character>()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                for (item in body.items) {
                    val completeCharacter = readOneName(item.name)
                    completeCharacter?.let {
                        finalist.add(it)
                    }
                }
            }
        }
        return finalist
    }

    override suspend fun raedPage(page: Int): List<Character> {
        val response = api.readPage(page)
        val finalist = mutableListOf<Character>()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                for (item in body.items) {
                    val completeCharacter = readOneName(item.name)
                    completeCharacter?.let {
                        finalist.add(it)
                    }
                }
            }
        }
        return finalist
    }

    private suspend fun readOneName(name: String): Character? {
        val response = api.readOne(name)
        return response.body()?.let { characterList ->
            characterList.firstOrNull()?.toExternal()
        }
    }
    override suspend fun readOne(id: Long): Character? {
        val response = api.readOne(id)
        return response.body()?.let { it.toExternal() }
    }
}

fun CharacterRemote.toExternal(): Character {
    return Character(
        id = this.id,
        name = this.name,
        ki = this.ki,
        maxKi = this.maxKi,
        race = this.race,
        gender = this.gender,
        description = this.description,
        image = this.image,
        affiliation = this.affiliation
    )
}
