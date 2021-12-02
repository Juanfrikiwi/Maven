package com.example.marvelcharacters.data.dataRepository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.data.network.MarvelPagingSource
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(private val service: MarvelService) :
    CharactersRepository {
    override suspend fun getListCharacter(): Flow<PagingData<CharactersEntity>> {
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