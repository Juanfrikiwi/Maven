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
import androidx.test.filters.SmallTest
import com.example.marvelcharacters.MainCoroutineRule
import com.example.marvelcharacters.data.local.database.MarvelDatabase
import com.example.marvelcharacters.utils.characterA
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
class DetailFavoritesViewModelTest {
    @Inject
    @Named("test_db")
    lateinit var appDatabase: MarvelDatabase

    private val hiltRule = HiltAndroidRule(this)
    private val coroutineRule = MainCoroutineRule()
    private val instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    val rule = RuleChain
        .outerRule(hiltRule)
        .around(instantTaskExecutorRule)
        .around(coroutineRule)

    @Inject
    lateinit var charactersFavouritesRepository: CharactersFavouritesRepository


    @Before
    fun setUp() {
        hiltRule.inject()
    }

    @After
    fun tearDown() {
        appDatabase.clearAllTables()
    }

    @Test
    fun getCharacterTest() = runBlocking {
        charactersFavouritesRepository.insertFavouriteCharacter(characterA)
        TestCase.assertEquals(charactersFavouritesRepository.getFavouriteCharacter(characterA.idCharacter).first().name,characterA.name)
        charactersFavouritesRepository.deleteAllFavouriteCharacter()
    }


}
