package com.example.dragonballapiandroid.data.local
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities= [CharacterEntity::class], version =1)
abstract class CharacterDataBase(): RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
}