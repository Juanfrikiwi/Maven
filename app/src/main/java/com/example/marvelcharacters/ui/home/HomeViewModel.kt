package com.example.marvelcharacters.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.MarvelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MarvelRepository
) : ViewModel() {
    fun getListCharacters(): Flow<PagingData<CharactersEntity>> {
        return repository.getListCharacters().cachedIn(viewModelScope)
    }
}