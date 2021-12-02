package com.example.marvelcharacters.domain.repository


import androidx.paging.PagingData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {
    suspend fun getListCharacter() : Flow<PagingData<CharactersEntity>>
}