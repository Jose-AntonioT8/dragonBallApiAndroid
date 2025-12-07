package com.example.dragonballapiandroid.data.local
import com.example.dragonballapiandroid.data.PlanetDataSource
import com.example.dragonballapiandroid.data.model.Planet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class PlanetLocalDataSource @Inject constructor(
    private val scope : CoroutineScope,
    private val planetDao: PlanetDao
): PlanetDataSource {
    override suspend fun addAll(planetList: List<Planet>){
        planetList.forEach{
                planet ->
            val entity = planet.toEntity()
            withContext(Dispatchers.IO){
                planetDao.insert(entity)
            }
        }

    }

    override fun observe(): Flow<Result<List<Planet>>> {
        val databaseFlow = planetDao.observeAll()
        return databaseFlow.map{
                entities ->
            Result.success(entities.toModel())
        }
    }

    override suspend fun readAll(): Result<List<Planet>> {
        val result = Result.success(planetDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: Long): Result<Planet> {
        val entity = planetDao.readPlanetById(id)
        return if(entity==null){
            Result.failure(PlanetNotFoundException())
        }
        else
            Result.success(entity.toModel())

    }

    override suspend fun isError() {
        TODO("Not yet implemented")
    }

    override suspend fun insert(planet: Planet) {
        val entity = planet.toEntity()
        planetDao.insert(entity)
    }

    override suspend fun raedPage(page: Int): List<Planet> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Long) {
        planetDao.delete(readOne(id).toEntity())

    }
}

private fun Result<Planet>.toEntity(): PlanetEntity {
    return this.getOrThrow().toEntity()
}
