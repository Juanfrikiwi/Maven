package com.example.marvelcharacters.ui.favourites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
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