package com.example.marvelcharacters.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.example.marvelcharacters.data.local.database.MarvelDatabase
import com.example.marvelcharacters.utils.characterA
import com.example.marvelcharacters.utils.characterB
import com.example.marvelcharacters.utils.characterC
import com.example.marvelcharacters.utils.characterD
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class FavoritesRepositoryTest {
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

    @After
    fun tearDown() {
        repository.deleteAllFavouriteCharacter()
        database.close()
    }


    @Test
    fun testRepositoryIsExistId() = runBlocking {
        assertEquals(repository.isExistId(characterC.idCharacter).first(), true)
    }


    @Test
    fun testRepositoryGetListCharacters() = runBlocking {
        assertEquals(repository.getFavouritesCharacters().first()[0], characterA)
        assertEquals(repository.getFavouritesCharacters().first()[1], characterB)
        assertEquals(repository.getFavouritesCharacters().first()[2], characterC)
    }

    @Test
    fun testRepositoryGetCharacter() = runBlocking {
        assertEquals(repository.getFavouriteCharacter(characterA.idCharacter).first(), characterA)
    }

    @Test
    fun testRepositoryInsertCharacter() = runBlocking {
        repository.insertFavouriteCharacter(characterD)
        assertEquals(repository.getFavouriteCharacter(characterD.idCharacter).first(), characterD)
    }

    @Test
    fun testRepositoryDeleteCharacter() = runBlocking {
        repository.deleteFavouriteCharacter(characterD)
        assertEquals(repository.getFavouritesCharacters().first().size, 3)
    }

    @Test
    fun testRepositoryDeleteAllCharacter() = runBlocking {
        repository.deleteAllFavouriteCharacter()
        assertEquals(repository.getFavouritesCharacters().first().size, 0)
    }

}