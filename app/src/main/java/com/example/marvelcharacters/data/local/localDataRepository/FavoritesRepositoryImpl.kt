package com.example.marvelcharacters.data.local.localDataRepository
import com.example.marvelcharacters.data.local.database.CharactersDao
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FavoritesRepositoryImpl @Inject constructor(private val charactersDao: CharactersDao) :
    FavoritesRepository {
    override suspend fun getFavouritesCharacters(): Flow<List<CharactersEntity>> {
        return charactersDao.getListCharacters()
    }

    override suspend fun isExistId(characterId: Int): Flow<Boolean> {
        return charactersDao.isExitsId(characterId)
    }

    override suspend fun getFavouriteCharacter(characterId: Int): Flow<CharactersEntity> {
        return charactersDao.getCharacter(characterId)
    }

    override suspend fun insertFavouriteCharacter(character: CharactersEntity) {
        charactersDao.insertCharacter(character)
    }

    override suspend fun deleteFavouriteCharacter(character: CharactersEntity) {
        charactersDao.deleteCharacter(character)
    }

    override suspend fun deleteAllFavouriteCharacter() {
        charactersDao.deleteListCharacters()
    }

    override suspend fun insertAll(characters: List<CharactersEntity>) {
        charactersDao.insertAll(characters)
    }

}