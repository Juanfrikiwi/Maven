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
    val errorResponse = MutableLiveData<String>()
    val successResponse = MutableLiveData<List<CharactersResponse>>()

    val characterId: Int = savedStateHandle.get<Int>(PLANT_ID_SAVED_STATE_KEY)!!
    val character = localRepository.getFavouriteCharacter(characterId).asLiveData()
    val isFavorite = localRepository.isExistId(characterId).asLiveData()


    suspend fun getCharacter(id: Int){
        try {
            successResponse.value = repository.getCharacter(id)
        } catch (e: Exception) {
            errorResponse.value = e.message
        }
    }
    suspend fun addFavourite(character: CharactersEntity): Boolean {
        return try {
            viewModelScope.launch {
                localRepository.insertFavouriteCharacter(
                    character = character
                )
            }
            true
        } catch (e: Exception) {
            false
        }
    }


    companion object {
        private const val PLANT_ID_SAVED_STATE_KEY = "characterId"
    }

}