package com.example.marvelcharacters.data.local.localDataRepository
import com.example.marvelcharacters.data.local.database.CharactersDao
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import com.example.marvelcharacters.utilities.Mappers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(private val charactersDao: CharactersDao) :
    FavoritesRepository {
    override suspend fun getFavouritesCharacters(): List<CharacterModel> {
        return Mappers.mapperListEntityToListCharacterModel(charactersDao.getListCharacters().first())
    }

    override suspend fun isExistId(characterId: Int): Flow<Boolean> {
        return charactersDao.isExitsId(characterId)
    }

    override suspend fun getFavouriteCharacter(characterId: Int): CharacterModel{
        return Mappers.mapperCharacterEntityToCharacterModel(charactersDao.getCharacter(characterId).first())
    }

    override suspend fun insertFavouriteCharacter(character: CharacterModel) {
        charactersDao.insertCharacter(Mappers.mapperCharacterModelToCharacterEntity(character))
    }

    override suspend fun deleteFavouriteCharacter(character: CharacterModel) {
        charactersDao.deleteCharacter(character.idCharacter)
    }

    override suspend fun deleteAllFavouriteCharacter() {
        charactersDao.deleteListCharacters()
    }

    override suspend fun insertAll(characters: List<CharacterModel>) {
        charactersDao.insertAll(Mappers.mapperListCharacterModelToListCharactersEntity(characters))
    }

}