package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.models.CharactersResponse
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: MarvelRepository,
    private val localRepository: CharactersFavouritesRepository
) : ViewModel() {

    suspend fun getCharacter(id: Int): List<CharactersResponse> {
        return repository.getCharacter(id)
    }

    suspend fun addFavourite() {
        viewModelScope.launch {
           val isAdded =  localRepository.insertFavouriteCharacter(
                character = CharactersEntity(
                    idCharacter = 7,
                    name = "JuanFran",
                    description = "descripcion",
                    modified = "7/7/89",
                    resourceURI = "www.kiwi.com",
                    thumbnail_path = "www.kiwi.com",
                    comics = listOf("1", "2", "3")
                )
            )
        }

    }

}