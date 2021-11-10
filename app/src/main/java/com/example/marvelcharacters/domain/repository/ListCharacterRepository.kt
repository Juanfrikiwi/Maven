package com.example.marvelcharacters.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharacters.data.MarvelPagingSource
import com.example.marvelcharacters.data.MarvelService
import com.example.marvelcharacters.data.models.CharactersEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ListCharacterRepository @Inject constructor(service: MarvelService) {
    val service = service
        fun getListCharacters(): Flow<PagingData<CharactersEntity>> {
            return Pager(
                config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = {
                    MarvelPagingSource(service)
                }
            ).flow
        }

    companion object {
            private const val NETWORK_PAGE_SIZE = 25
        }
    }
