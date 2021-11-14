package com.example.marvelcharacters.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharacters.data.network.MarvelPagingSource
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.data.network.models.CharactersResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class MarvelRepository @Inject constructor(service: MarvelService) {
    val service = service
        fun getListCharacters(): Flow<PagingData<CharactersResponse>> {
            return Pager(
                config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = {
                    MarvelPagingSource(service)
                }
            ).flow
        }

    suspend fun getCharacter(id:Int): List<CharactersResponse> {
            val response = service.getCharacter(id)
            CharacterProvider.characters = response.data.characters
            return response.data.characters
        }

    class CharacterProvider {
        companion object {
            var characters:List<CharactersResponse> = emptyList()
        }
    }

    companion object {
            private const val NETWORK_PAGE_SIZE = 25
        }
    }
