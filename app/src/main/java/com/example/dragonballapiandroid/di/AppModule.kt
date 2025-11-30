package com.example.dragonballapiandroid.di
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.dragonballapiandroid.data.CharacterDataSource
import com.example.dragonballapiandroid.data.local.CharacterLocalDataSource
import com.example.dragonballapiandroid.data.remote.CharacterRemoteDataSource
import com.example.dragonballapiandroid.data.repository.CharacterRepository
import com.example.dragonballapiandroid.data.repository.CharacterRepositoryImpl
import javax.inject.Qualifier

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {

    @Binds
    @Singleton
    @RemoteDataSource
    abstract fun bindsRemotePokemonDataSource(ds: CharacterRemoteDataSource): CharacterDataSource

    @Binds
    @Singleton
    @LocalDataSource
    abstract fun bindsLocalPokemonDataSource(ds: CharacterLocalDataSource): CharacterDataSource

    @Binds
    @Singleton
    abstract  fun bindPokemonRepository(repository: CharacterRepositoryImpl): CharacterRepository

    //abstract fun bindPokemonRepository(repository: PokemonFakeRemoteRepository): PokemonRepository
    //abstract fun bindPokemonRepository(repository: PokemonInMemoryRepository): PokemonRepository
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class LocalDataSource

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RemoteDataSource