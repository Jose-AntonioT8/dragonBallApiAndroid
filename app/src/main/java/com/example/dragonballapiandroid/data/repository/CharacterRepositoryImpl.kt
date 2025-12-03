package com.example.dragonballapiandroid.data.repository

import com.example.dragonballapiandroid.data.model.Character
import com.example.dragonballapiandroid.data.CharacterDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.dragonballapiandroid.di.RemoteDataSource
import com.example.dragonballapiandroid.di.LocalDataSource
import kotlinx.coroutines.flow.onStart

class CharacterRepositoryImpl @Inject constructor(
    @RemoteDataSource private val remoteDataSource: CharacterDataSource,
    @LocalDataSource private val localDataSource: CharacterDataSource,
    private val scope: CoroutineScope
): CharacterRepository {
    override suspend fun readAll(): Result<List<Character>> {
        return remoteDataSource.readAll()
    }

    override suspend fun readOne(id: Long): Result<Character> {
        return localDataSource.readOne(id)
    }

    override suspend fun readPage(page: Int): List<Character> {
        return remoteDataSource.raedPage(page)

    }

    override fun observe(): Flow<Result<List<Character>>> {
        return localDataSource.observe()

    }

    override suspend fun delete(id: Long) {
        localDataSource.delete(id)

    }

    override suspend fun refresh() {
        val resultRemotePokemon = remoteDataSource.readAll()
        if (resultRemotePokemon.isSuccess) {
            localDataSource.addAll(resultRemotePokemon.getOrNull()!!)
        }
    }

    override suspend fun insert(character: Character) {
        localDataSource.insert(character)
    }
}