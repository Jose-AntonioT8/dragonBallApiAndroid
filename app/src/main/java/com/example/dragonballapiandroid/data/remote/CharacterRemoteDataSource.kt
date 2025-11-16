package com.example.dragonballapiandroid.data.remote

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
            // Usamos 'let' para trabajar de forma segura con el body, si no es nulo.
            response.body()?.let { body ->
                for (item in body.items) {
                    // Llamamos a nuestra función corregida para obtener el personaje completo
                    val completeCharacter = readOneName(item.name)
                    completeCharacter?.let {
                        finalist.add(it)
                    }
                }
            }
        }
        return finalist // Devuelve la lista (llena o vacía)
    }

    // ----- FUNCIÓN CORREGIDA -----
    private suspend fun readOneName(name: String): Character? {
        // Asegúrate de que llamas a la función correcta de tu API
        // (la que usa @Query y espera una List)
        val response = api.readOne(name)

        // Usamos 'let' para evitar los '!!' y los crashes
        return response.body()?.let { characterList ->
            // Si la lista no está vacía, coge el primer elemento y conviértelo.
            characterList.firstOrNull()?.toExternal()
        }
    }

    override suspend fun readOne(id: Long): Character? {
        val response = api.readOne(id)
        // También aquí es más seguro usar 'let'
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
