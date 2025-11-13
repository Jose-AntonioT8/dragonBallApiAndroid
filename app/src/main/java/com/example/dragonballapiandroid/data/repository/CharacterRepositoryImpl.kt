package com.example.dragonballapiandroid.data.repository

import com.example.dragonballapiandroid.data.model.Character
import com.example.dragonballapiandroid.data.remote.CharacterDataSource
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val dataSourve : CharacterDataSource
): CharacterRepository {
    override suspend fun readAll(): List<Character> {
        return dataSourve.readAll()
    }

    override suspend fun readOne(id: Long): Character? {
        return dataSourve.readOne(id)
    }

}