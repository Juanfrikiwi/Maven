package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.ListCharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel@Inject constructor(
    private val repository: ListCharacterRepository
): ViewModel() {
    suspend fun getCharacter(id: Int): List<CharactersEntity> {
            return repository.getCharacter(id)
        }

}