package com.example.marvelcharacters.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import com.example.marvelcharacters.data.network.MarvelService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.*
import javax.inject.Inject

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class MarvelRepositoryTest {

    @Inject
    lateinit var service: MarvelService

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlocking {
        hiltRule.inject()
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getCharacterTest() = runBlocking {
        assertEquals(service.getCharacter(1011334).data.characters.firstOrNull()?.name, "3-D Man")
    }

    @Test
    fun getListCharactersTest() = runBlocking {
        assertEquals(service.getListCharacters().data.totalPages, 1559)
    }

}