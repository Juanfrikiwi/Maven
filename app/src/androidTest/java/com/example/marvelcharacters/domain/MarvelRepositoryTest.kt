package com.example.marvelcharacters.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.example.marvelcharacters.data.local.MarvelDatabase
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.utils.characterA
import com.example.marvelcharacters.utils.characterB
import com.example.marvelcharacters.utils.characterC
import com.example.marvelcharacters.utils.characterD
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class CharactersFavoritesRepository {
    @Inject
    @Named("test_db")
    lateinit var database: MarvelDatabase

    @Inject
    lateinit var repository: CharactersFavouritesRepository

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()
        repository.insertAll(listOf(characterA,characterB,characterC))
    }

    @Test
    fun testRepositoryIsExistId() = runBlocking {
        assertEquals(repository.isExistId(3).first(), true)
    }


    @Test
    fun testRepositoryGetListCharacters() = runBlocking {
        assertEquals(repository.getFavouritesCharacters().first()[0], characterA)
    }

    @Test
    fun testRepositoryGetCharacter() = runBlocking {
        assertEquals(repository.getFavouriteCharacter(1).first(), characterA)
    }

    @Test
    fun testRepositoryInsertCharacter() = runBlocking {
        repository.insertFavouriteCharacter(characterD)
    }

    @Test
    fun testRepositoryDeleteCharacter() = runBlocking {
        repository.deleteFavouriteCharacter(characterD)
    }

    @After
    fun clean() {
    }

}