package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailFavoritesViewModel @Inject constructor(
    private val localRepository: CharactersFavouritesRepository,
) : ViewModel() {
    lateinit var character: LiveData<CharactersEntity>
    fun getCharacter(characterId: Int): LiveData<CharactersEntity> {
        return  localRepository.getFavouriteCharacter(characterId).asLiveData()
    }

}