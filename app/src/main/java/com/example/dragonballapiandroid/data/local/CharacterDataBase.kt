// En el archivo: .../data/local/CharacterDataBase.kt

package com.example.dragonballapiandroid.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CharacterEntity::class, PlanetEntity::class],
    version = 1,
)
abstract class CharacterDataBase : RoomDatabase() {
    abstract fun getCharacterDao(): CharacterDao
    abstract fun getPlanetDao(): PlanetDao
}
