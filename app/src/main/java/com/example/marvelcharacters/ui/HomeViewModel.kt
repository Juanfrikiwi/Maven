package com.example.marvelcharacters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.network.CharactersResponse
import com.example.marvelcharacters.domain.repository.ListCharacterRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeViewModel@Inject constructor(
    private val repository: ListCharacterRepository
): ViewModel() {

    fun getListCharacters(): Flow<PagingData<CharactersResponse>> {
        return repository.getListCharacters().cachedIn(viewModelScope)
    }

}