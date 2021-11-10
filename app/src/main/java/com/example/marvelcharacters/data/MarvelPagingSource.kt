package com.example.marvelcharacters.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.marvelcharacters.data.models.CharactersEntity
import java.math.BigInteger
import java.security.MessageDigest

private const val UNSPLASH_STARTING_PAGE_INDEX = 1

class MarvelPagingSource(
    private val service: MarvelService,
) : PagingSource<Int, CharactersEntity>() {

    var ts = "1"
    val pubkey = "3514f3813b164a2099f7dded753edcb0"
    val pvtkey = "926b3b0d3a931170235aa149dd59094e7b623bb5"
    fun md5(input:String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int,CharactersEntity> {
        val keyParams = "ts="+ts+"&apikey="+pubkey+"&hash="+md5(ts+pubkey+pvtkey)
        val page = params.key ?: UNSPLASH_STARTING_PAGE_INDEX
        return try {
            val response = service.getListCharacters(keyParams)
            val characters = response.data.characters
            LoadResult.Page(
                data = characters,
                prevKey = if (page == UNSPLASH_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (page == response.data.totalPages) null else page + 1

            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CharactersEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            // This loads starting from previous page, but since PagingConfig.initialLoadSize spans
            // multiple pages, the initial load will still load items centered around
            // anchorPosition. This also prevents needing to immediately launch prepend due to
            // prefetchDistance.
            state.closestPageToPosition(anchorPosition)?.prevKey
        }
    }
}
