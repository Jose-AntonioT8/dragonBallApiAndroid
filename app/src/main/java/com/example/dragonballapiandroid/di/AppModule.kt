package com.example.dragonballapiandroid.di
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.dragonballapiandroid.data.remote.CharacterDataSource
import com.example.dragonballapiandroid.data.remote.CharacterRemoteDataSource
import com.example.dragonballapiandroid.data.repository.CharacterRepository
import com.example.dragonballapiandroid.data.repository.CharacterRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    abstract fun characterDataSource(ds: CharacterRemoteDataSource): CharacterDataSource

    @Binds
    @Singleton
    abstract  fun bindCharacterRepository(repository: CharacterRepositoryImpl): CharacterRepository

    //abstract fun bindCharacterRepository(repository: CharacterFakeRemoteRepository): CharacterRepository
    //abstract fun bindCharacterRepository(repository: CharacterInMemoryRepository): CharacterRepository
}