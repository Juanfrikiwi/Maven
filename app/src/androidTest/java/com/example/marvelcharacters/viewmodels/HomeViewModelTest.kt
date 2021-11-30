/*
 * Copyright 2018 Google LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.marvelcharacters.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.marvelcharacters.MainCoroutineRule
import com.example.marvelcharacters.data.local.MarvelDatabase
import com.example.marvelcharacters.data.network.MarvelService
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.domain.repository.MarvelRepository
import com.example.marvelcharacters.runBlockingTest
import com.example.marvelcharacters.ui.HomeAdapter
import com.example.marvelcharacters.ui.detail.DetailViewModel
import com.example.marvelcharacters.ui.home.HomeViewModel
import com.example.marvelcharacters.utils.characterA
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.internal.util.NotificationLite.getValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class HomeViewModelTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var marvelRepository: MarvelRepository

    @Inject
    lateinit var marvelService: MarvelService

    var adapter: HomeAdapter = HomeAdapter()

    @Before
    fun setUp() {
        hiltRule.inject()
        marvelRepository = MarvelRepository(marvelService)
    }

    @After
    fun tearDown() {

    }

    @Test
    fun getCharacterTest() = runBlocking {
            TestCase.assertEquals(marvelRepository.getCharacter(1011334).first().name,"3-D Man")
    }

    @Test
    fun getListCharactersTest() = runBlocking {
        // You need to launch here because submitData suspends forever while PagingData is alive
        val job = launch {
            marvelRepository.getListCharacters().collectLatest {
                adapter.submitData(it)
            }
            assertEquals(adapter.snapshot().items[0].name,"3-D Man")
        }
        // We need to cancel the launched job as coroutines.test framework checks for leaky jobs
        job.cancel()
    }

}
