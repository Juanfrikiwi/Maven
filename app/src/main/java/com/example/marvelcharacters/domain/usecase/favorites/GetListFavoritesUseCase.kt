package com.example.marvelcharacters.domain.usecase.favorites


import androidx.paging.PagingData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.CharactersRepository
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke() : Flow<List<CharactersEntity>> {
        return favoritesRepository.getFavouritesCharacters()
    }
}