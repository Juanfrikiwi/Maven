package com.example.marvelcharacters.domain.repository


import com.example.marvelcharacters.data.local.models.CharactersEntity
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getFavouritesCharacters() : Flow<List<CharactersEntity>>

    suspend fun isExistId(characterId: Int) : Flow<Boolean>

    suspend fun getFavouriteCharacter(characterId: Int): Flow<CharactersEntity>

    suspend fun insertFavouriteCharacter(character: CharactersEntity)

    suspend fun deleteFavouriteCharacter(character: CharactersEntity)

    suspend fun deleteAllFavouriteCharacter()

    suspend fun insertAll(characters: List<CharactersEntity>)

}