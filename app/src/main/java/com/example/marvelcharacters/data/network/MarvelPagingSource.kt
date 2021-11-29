package com.example.marvelcharacters.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelcharacters.data.network.models.CharactersResponse

class MarvelPagingSource(
    private val service: MarvelService,
) : PagingSource<Int, CharactersResponse>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,CharactersResponse> {
        return try {
            val response = service.getListCharacters()
            val characters = response.data.characters
            LoadResult.Page(
                data = characters,
                prevKey = null,
                nextKey = null
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharactersResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
