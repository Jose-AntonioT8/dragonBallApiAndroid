package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.CharacterDataSource
import com.example.dragonballapiandroid.data.model.Character
import com.example.dragonballapiandroid.data.remote.model.CharacterRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject
class CharacterRemoteDataSource @Inject constructor(
    private val api: CharacterApi,
    private val scope: CoroutineScope

) : CharacterDataSource {
    override suspend fun addAll(characterList: List<Character>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Character>>> {
        return flow {
            emit(Result.success(listOf<Character>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Character>> {
        try {
            val response = api.readAll()
            val finalList = mutableListOf<Character>()
            return if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.items) {
                    val remoteCharacter = readOneName(name = result.name)
                    remoteCharacter?.let {
                        finalList.add(it)
                    }
                }
                Result.success(finalList)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
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
    override suspend fun readOne(id: Long): Result<Character> {
        try {
            val response = api.readOne(id)
            return if (response.isSuccessful) {
                val character = response.body()!!.toExternal()
                Result.success(character)
            } else {
                Result.failure(RuntimeException("Error code: ${response.code()}"))
            }
        } catch (e: Exception) {
            return Result.failure(e)
        }
    }

    override suspend fun isError() {
        TODO("Not yet implemented")
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
