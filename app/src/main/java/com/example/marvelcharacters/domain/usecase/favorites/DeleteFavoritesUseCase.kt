package com.example.marvelcharacters.domain.usecase.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class DeleteFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke(character : CharacterModel):Boolean{
        return try {
            favoritesRepository.deleteFavouriteCharacter(character)
            true
        } catch (e: Exception) {
            false
        }
    }
}