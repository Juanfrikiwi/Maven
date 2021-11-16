package com.example.marvelcharacters.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
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

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()
        characterDao = database.charactersDao()
        characterDao.insertCharacter(characterA)
        characterDao.insertCharacter(characterB)
        characterDao.insertCharacter(characterC)
    }


    @Test
    suspend fun getListCharacters() {
        val list = characterDao.getListCharacters().first()
        assertEquals(list.size, 3)
        assertEquals(list[0],characterA)
        assertEquals(list[1],characterB)
        assertEquals(list[2],characterC)
    }


    @After
    fun clean() {
    }


}