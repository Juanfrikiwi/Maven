package com.example.marvelcharacters.di

import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.domain.repository.MarvelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TestDataNetworkModule {
    @Provides
    @Singleton
    @Named("test_service")
    fun provideMarvelService(): MarvelService {
        return MarvelService.create()
    }

    @Provides
    @Singleton
    @Named("test_repository")
    fun provideMarvelRepository(marvelService: MarvelService): MarvelRepository {
        return MarvelRepository(marvelService)
    }


}