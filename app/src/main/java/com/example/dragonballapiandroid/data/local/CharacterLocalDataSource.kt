package com.example.dragonballapiandroid.data.local

import com.example.dragonballapiandroid.data.CharacterDataSource
import com.example.dragonballapiandroid.data.local.toEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

import com.example.dragonballapiandroid.data.model.Character

class CharacterLocalDataSource @Inject constructor(
    private val scope : CoroutineScope,
    private val characterDao: CharacterDao
): CharacterDataSource {
    override suspend fun addAll(characterList: List<Character>){
        characterList.forEach{
            character ->
                val entity = character.toEntity()
                withContext(Dispatchers.ID){
                    characterDao.insert(entity)
                }
        }

    }

    override suspend fun readAll(): Result<List<Character>> {
        val result = Result.success(characterDao.getAll().toModel())
        return result
    }

    override suspend fun readOne(id: Long): Result<Character> {
        val entity = characterDao.readCharacterById(id)
        return if(entity==null){
            Result.failure(CharacterNotFoundException())
        }
        else
            Result.success(entity.toModel())

    }

    override suspend fun raedPage(page: Int): List<Character> {
        TODO("Not yet implemented")
    }
}