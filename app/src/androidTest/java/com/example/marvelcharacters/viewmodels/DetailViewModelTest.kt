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
import androidx.room.Room
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.example.marvelcharacters.MainCoroutineRule
import com.example.marvelcharacters.data.local.MarvelDatabase
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.domain.repository.MarvelRepository
import com.example.marvelcharacters.runBlockingTest
import com.example.marvelcharacters.ui.detail.DetailViewModel
import com.example.marvelcharacters.utils.characterA
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.reactivex.internal.util.NotificationLite.getValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject
import javax.inject.Named
import kotlin.jvm.Throws

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DetailViewModelTest {
    @Inject
    @Named("test_db")
    lateinit var appDatabase: MarvelDatabase

    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()
    private val instantTaskExecutorRule = InstantTaskExecutorRule()
    lateinit var viewModel: DetailViewModel


    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var marvelRepository: MarvelRepository

    @Inject
    lateinit var charactersFavouritesRepository: CharactersFavouritesRepository


    @Before
    fun setUp() {
        hiltRule.inject()
        val savedStateHandle: SavedStateHandle = SavedStateHandle().apply {
            set("characterId", characterA.idCharacter)
        }
        viewModel = DetailViewModel(marvelRepository, charactersFavouritesRepository, savedStateHandle)
    }

    @After
    fun tearDown() {
        appDatabase.close()
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    @Test
    @Throws(InterruptedException::class)
    fun testDetailViewModel() = coroutineRule.runBlockingTest {
        assertFalse(getValue(viewModel.isFavorite.asFlow().first()))
    }

}
