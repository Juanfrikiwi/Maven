package com.example.marvelcharacters.domain.usecase.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.FavoritesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AddFavoritesUseCase @Inject constructor(private val favoritesRepository: FavoritesRepository) {
    suspend fun invoke(character : CharacterModel){
        favoritesRepository.insertFavouriteCharacter(character)
    }
}