package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.remote.model.CharacterRemote
import javax.inject.Inject
import com.example.dragonballapiandroid.data.model.Character
import com.example.dragonballapiandroid.data.remote.model.CharacterListItemRemote


class CharacterRemoteDataSource@Inject constructor(
    private val api: CharacterApi
): CharacterDataSource {
    override suspend fun readAll(): List<Character> {
        val response = api.readAll()
        val finalist = mutableListOf<Character>()
        return if (response.isSuccessful) {
            val body = response.body()!!
            for (result in body.results) {
                val remoteCharacter = readOneName(name = result.name)
                remoteCharacter?.let{
                    finalist.add(it)

                }
            }
            finalist
        }
        else{
            listOf<Character>()
        }
    }
    private suspend fun readOneName(name: String): Character? {
        val response = api.readOne(name)
        return if (response.isSuccessful) {
            response.body()!!.toExternal()
        }
        else {
            null
        }
    }

    override suspend fun readOne(id: Long): Character? {
        val response = api.readOne(id)
        return if (response.isSuccessful) {
            response.body()!!.toExternal()
        }
        else {
            null
        }
    }

}
fun CharacterRemote.toExternal():Character{
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