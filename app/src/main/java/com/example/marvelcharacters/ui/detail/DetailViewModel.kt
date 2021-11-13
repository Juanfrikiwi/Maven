package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.network.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    private val repository: MarvelRepository
): ViewModel() {
    suspend fun getCharacter(id: Int): List<CharactersEntity> {
            return repository.getCharacter(id)
        }

}