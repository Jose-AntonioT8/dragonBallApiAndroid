package com.example.dragonballapiandroid.data.local
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface PlanetDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(planet: PlanetEntity)

    @Delete
    suspend fun delete(planet: PlanetEntity):Int

    @Query("SELECT * FROM planet")
    fun getAll(): List<PlanetEntity>

    @Query("SELECT * FROM planet")
    fun observeAll(): Flow<List<PlanetEntity>>

    @Query("SELECT * FROM planet WHERE id = :id")
    suspend fun readPlanetById(id: Long): PlanetEntity?
}