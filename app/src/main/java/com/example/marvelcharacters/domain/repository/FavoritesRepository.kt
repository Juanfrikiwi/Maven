package com.example.marvelcharacters.domain.repository


import com.example.marvelcharacters.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

interface FavoritesRepository {
    suspend fun getFavouritesCharacters() : List<CharacterModel>

    suspend fun isExistId(characterId: Int) : Flow<Boolean>

    suspend fun getFavouriteCharacter(characterId: Int): CharacterModel

    suspend fun insertFavouriteCharacter(character: CharacterModel)

    suspend fun deleteFavouriteCharacter(character: CharacterModel)

    suspend fun deleteAllFavouriteCharacter()

    suspend fun insertAll(characters: List<CharacterModel>)

}