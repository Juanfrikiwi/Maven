package com.example.marvelcharacters.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharacters.data.MarvelDataSource
import com.example.marvelcharacters.data.MarvelService
import com.example.marvelcharacters.data.models.Characters
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class ListCharacterRepository @Inject constructor(service: MarvelService) {
    val service = service
        fun getListCharacters(): Flow<PagingData<Characters>> {
            return Pager(
                config = PagingConfig(enablePlaceholders = false, pageSize = NETWORK_PAGE_SIZE),
                pagingSourceFactory = { MarvelDataSource(service) }
            ).flow
        }


    companion object {
            private const val NETWORK_PAGE_SIZE = 25
        }
    }
