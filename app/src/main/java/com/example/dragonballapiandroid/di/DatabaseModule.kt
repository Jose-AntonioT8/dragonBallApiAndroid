package com.example.dragonballapiandroid.di

import android.content.Context
import androidx.room.Room
import com.example.dragonballapiandroid.data.local.CharacterDao
import com.example.dragonballapiandroid.data.local.CharacterDataBase
import com.example.dragonballapiandroid.data.local.PlanetDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext applicationContext: Context
    ): CharacterDataBase {

        val database = Room.databaseBuilder(context = applicationContext,
            CharacterDataBase::class.java,
            name = "character-db")
            .fallbackToDestructiveMigration(true)
            .build()
        return database
    }

    @Provides
    fun provideCharacterDao(
        database: CharacterDataBase
    ): CharacterDao {
        return database.getCharacterDao()
    }
    @Provides
    fun providePlanetDao(
        database: CharacterDataBase
    ): PlanetDao {
        return database.getPlanetDao()
    }

}