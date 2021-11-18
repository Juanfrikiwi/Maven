package com.example.marvelcharacters.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: CharactersFavouritesRepository
) : ViewModel() {
    val characters: LiveData<List<CharactersEntity>> =
        repository.getFavouritesCharacters().asLiveData()

    fun deleteCharacter(character:CharactersEntity):Boolean{
        return try {
            viewModelScope.launch {
                repository.deleteFavouriteCharacter(
                    character = character
                )
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}