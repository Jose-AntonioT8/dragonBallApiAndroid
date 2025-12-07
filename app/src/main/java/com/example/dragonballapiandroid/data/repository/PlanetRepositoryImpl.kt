package com.example.dragonballapiandroid.data.repository


import com.example.dragonballapiandroid.data.model.Planet
import com.example.dragonballapiandroid.data.PlanetDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.dragonballapiandroid.di.RemoteDataSource
import com.example.dragonballapiandroid.di.LocalDataSource
import kotlinx.coroutines.flow.onStart

class PlanetRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: PlanetDataSource,
    @LocalDataSource private val localDataSource: PlanetDataSource,
    private val scope: CoroutineScope
): PlanetRepository {
    override suspend fun readAll(): Result<List<Planet>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: Long): Result<Planet> {
        return localDataSource.readOne(id)
    }

    override suspend fun readPage(page: Int): List<Planet> {
        return remoteDataSource.raedPage(page)

    }

    override fun observe(): Flow<Result<List<Planet>>> {
        return localDataSource.observe()

    }

    override suspend fun delete(id: Long) {
        localDataSource.delete(id)

    }

    override suspend fun refresh() {
        val resultRemotePlanet = remoteDataSource.readAll()
        if (resultRemotePlanet.isSuccess) {
            localDataSource.addAll(resultRemotePlanet.getOrNull()!!)
        }
    }

    override suspend fun insert(planet: Planet) {
        localDataSource.insert(planet)
    }
}