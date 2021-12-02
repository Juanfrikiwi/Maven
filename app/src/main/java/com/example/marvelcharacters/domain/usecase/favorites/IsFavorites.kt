package com.example.marvelcharacters.domain.usecase.favorites

import com.example.marvelcharacters.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsFavorites @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke(id:Int) : Flow<Boolean> {
        return favoritesRepository.isExistId(id)
    }
}