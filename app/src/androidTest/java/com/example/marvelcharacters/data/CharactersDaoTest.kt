package com.example.marvelcharacters.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.example.marvelcharacters.data.local.CharactersDao
import com.example.marvelcharacters.data.local.MarvelDatabase
import com.example.marvelcharacters.data.local.models.CharactersEntity
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
class CharactersDaoTest {
    @Inject
    @Named("test_db")
    lateinit var database: MarvelDatabase
    private lateinit var characterDao: CharactersDao

    private val characterA =
        CharactersEntity(1, "A", "Prueba1", "07/07/89", "www.kiwi.es", "www.kiwi.es", listOf("1"))
    private val characterB =
        CharactersEntity(2, "B", "Prueba2", "07/07/89", "www.kiwi.es", "www.kiwi.es", listOf("1"))
    private val characterC =
        CharactersEntity(3, "C", "Prueba3", "07/07/89", "www.kiwi.es", "www.kiwi.es", listOf("1"))
    private val characterD =
        CharactersEntity(4, "D", "Prueba4", "07/07/89", "www.kiwi.es", "www.kiwi.es", listOf("1"))

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()
        characterDao = database.charactersDao()
        characterDao.insertAll(listOf(characterA,characterB,characterC))
    }

    @Test
    fun testIsExistId() = runBlocking {
        assertEquals(characterDao.isExitsId(3).first(), true)
    }


    @Test
    fun testGetListCharacters() = runBlocking {
        assertEquals(characterDao.getListCharacters().first().size, 3)
        assertEquals(characterDao.getListCharacters().first()[0], characterA)
        assertEquals(characterDao.getListCharacters().first()[1], characterB)
        assertEquals(characterDao.getListCharacters().first()[2], characterC)
    }

    @Test
    fun testGetCharacter() = runBlocking {
        assertEquals(characterDao.getCharacter(characterA.idCharacter).first(), characterA)
    }

    @Test
    fun testInsertCharacter() = runBlocking {
        characterDao.insertCharacter(characterD)
        assertEquals(characterDao.getListCharacters().first().size,4)
    }

    @Test
    fun testDeleteCharacter() = runBlocking {
        characterDao.deleteCharacter(characterD)
        assertEquals(characterDao.getListCharacters().first().size,3)
    }

    @After
    fun clean() {
    }

}