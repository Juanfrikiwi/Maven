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
    private val localRepository: CharactersFavouritesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val characterId: Int = savedStateHandle.get<Int>(PLANT_ID_SAVED_STATE_KEY)!!
    val character = localRepository.getFavouriteCharacter(characterId).asLiveData()

    suspend fun getCharacter(id: Int): List<CharactersResponse>? {
        return try {
            repository.getCharacter(id)
        } catch (e: Exception) {
            null
        }
    }

    suspend fun addFavourite(): Boolean {
        try {
            viewModelScope.launch {
                localRepository.insertFavouriteCharacter(
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
            return true
        } catch (e: Exception) {
            return false
        }
    }

    companion object {
        private const val PLANT_ID_SAVED_STATE_KEY = "characterId"
    }

}