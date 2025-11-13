package com.example.dragonballapiandroid.di

import com.example.dragonballapiandroid.data.remote.CharacterApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
class RemoteModule {
    @Provides
    @Singleton
    fun provideCharacterApi(): CharacterApi{
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dragonball-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(CharacterApi::class.java)
    }
}