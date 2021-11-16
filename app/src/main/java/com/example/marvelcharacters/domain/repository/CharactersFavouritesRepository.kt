
package com.example.marvelcharacters.domain.repository

import com.example.marvelcharacters.data.local.CharactersDao
import com.example.marvelcharacters.data.local.models.CharactersEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharactersFavouritesRepository @Inject constructor(private val charactersDao: CharactersDao) {

    fun getFavouritesCharacters() = charactersDao.getListCharacters()

    fun isExistId(characterId: Int) = charactersDao.isExitsId(characterId)

    fun getFavouriteCharacter(characterId: Int) = charactersDao.getCharacter(characterId)

    suspend fun insertFavouriteCharacter(character: CharactersEntity) {
        charactersDao.insertCharacter(character)
    }

    suspend fun deleteFavouriteCharacter(character: CharactersEntity) {
        charactersDao.deleteCharacter(character)
    }

}
