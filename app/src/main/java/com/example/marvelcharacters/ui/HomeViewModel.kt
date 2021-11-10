package com.example.marvelcharacters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.ListCharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repository: ListCharacterRepository
): ViewModel() {
    private var currentSearchResult: Flow<PagingData<CharactersEntity>>? = null
    fun getListCharacters(): Flow<PagingData<CharactersEntity>> {
        val newResult: Flow<PagingData<CharactersEntity>> =
        repository.getListCharacters().cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }

}