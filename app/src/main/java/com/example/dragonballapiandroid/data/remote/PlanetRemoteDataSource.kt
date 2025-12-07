package com.example.dragonballapiandroid.data.remote

import com.example.dragonballapiandroid.data.PlanetDataSource
import com.example.dragonballapiandroid.data.model.Planet
import com.example.dragonballapiandroid.data.remote.model.PlanetRemote
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class PlanetRemoteDataSource @Inject constructor(
    private val api: PlanetApi,
    private val scope: CoroutineScope

) : PlanetDataSource {
    override suspend fun addAll(planetList: List<Planet>) {
        TODO("Not yet implemented")
    }

    override fun observe(): Flow<Result<List<Planet>>> {
        return flow {
            emit(Result.success(listOf<Planet>()))
            val result = readAll()
            emit(result)
        }.shareIn(
            scope = scope,
            started = SharingStarted.WhileSubscribed(5_000L),
            replay = 1
        )
    }

    override suspend fun readAll(): Result<List<Planet>> {
        try {
            val response = api.readAll()
            val finalList = mutableListOf<Planet>()
            return if (response.isSuccessful) {
                val body = response.body()!!
                for (result in body.items) {
                    val remotePlanet = readOneName(name = result.name)
                    remotePlanet?.let {
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

    override suspend fun raedPage(page: Int): List<Planet> {
        val response = api.readPage(page)
        val finalist = mutableListOf<Planet>()

        if (response.isSuccessful) {
            response.body()?.let { body ->
                for (item in body.items) {
                    val completePlanet = readOneName(item.name)
                    completePlanet?.let {
                        finalist.add(it)
                    }
                }
            }
        }
        return finalist
    }

    override suspend fun delete(id: Long) {
        api.delete(id)
    }

    private suspend fun readOneName(name: String): Planet? {
        val response = api.readOne(name)
        return response.body()?.let { planetList ->
            planetList.firstOrNull()?.toExternal()
        }
    }
    override suspend fun readOne(id: Long): Result<Planet> {
        try {
            val response = api.readOne(id)
            return if (response.isSuccessful) {
                val planet = response.body()!!.toExternal()
                Result.success(planet)
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

    override suspend fun insert(planet: Planet) {
        api.insert(planet.toRemote())
    }
}

private fun Planet.toRemote(): PlanetRemote {
    return PlanetRemote(
        id = this.id,
        name = this.name,
        description = this.description,
        image = this.image,
        isDestroyed = this.isDestroyed
    )
}

fun PlanetRemote.toExternal(): Planet {
    return Planet(
        id = this.id,
        name = this.name,
        isDestroyed = this.isDestroyed,
        description = this.description,
        image = this.image,
    )
}
