package com.example.marvelcharacters.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.models.Characters
import com.example.marvelcharacters.domain.repository.ListCharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repository: ListCharacterRepository
): ViewModel() {
    fun getListCharacters(): Flow<PagingData<Characters>> {
        return repository.getListCharacters().cachedIn(viewModelScope)
    }

}