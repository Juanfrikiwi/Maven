package com.example.marvelcharacters.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharacters.data.network.CharactersResponse
import com.example.marvelcharacters.data.network.ListCharactersProvider
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.data.network.MarvelSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ListCharacterRepository @Inject constructor(service: MarvelService) {
    val service = service
        fun getListCharacters():  Flow<PagingData<CharactersResponse>> {
            return Pager(
                config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = { MarvelSource(service) }
            ).flow
        }

        companion object {
            private const val NETWORK_PAGE_SIZE = 25
        }
    }
