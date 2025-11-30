package com.example.dragonballapiandroid.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface CharacterDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(character: CharacterEntity)

    @Delete
    suspend fun delete(character: CharacterEntity):Int

    @Query("SELECT * FROM character")
    fun getAll(): List<CharacterEntity>

    @Query("SELECT * FROM character")
    fun observeAll(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM character WHERE id = :id")
    suspend fun readCharacterById(id: Long): CharacterEntity?




}