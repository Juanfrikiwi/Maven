package com.example.marvelcharacters.ui.favourites

import androidx.lifecycle.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailFavoritesViewModel @Inject constructor(
    private val localRepository: CharactersFavouritesRepository,
) : ViewModel() {
    lateinit var character: LiveData<CharactersEntity>
    suspend fun addFavourite(character: CharactersEntity): Boolean {
        try {
            viewModelScope.launch {
                localRepository.insertFavouriteCharacter(
                    character = character
                )
            }
            return true
        } catch (e: Exception) {
            return false
        }
    }

    fun getCharacter(characterId: Int) {
        character = localRepository.getFavouriteCharacter(characterId).asLiveData()
    }

}