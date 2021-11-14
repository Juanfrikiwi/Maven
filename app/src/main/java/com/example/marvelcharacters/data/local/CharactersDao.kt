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

package com.example.marvelcharacters.data.local

import androidx.room.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import kotlinx.coroutines.flow.Flow

/**
 * The Data Access Object for the [Character] class.
 */
@Dao
interface CharactersDao {
    @Query("SELECT * FROM characters")
    fun getListCharacters(): Flow<List<CharactersEntity>>

    @Query("SELECT * FROM characters WHERE id = :characterId")
    fun getCharacter(characterId: Int): Flow<CharactersEntity>

    @Query("SELECT EXISTS(SELECT 1 FROM characters WHERE id = id LIMIT 1)")
    fun isExitsId(): Flow<Boolean>

    @Insert
    suspend fun insertCharacter(charactersEntity: CharactersEntity): Long

    @Delete
    suspend fun deleteCharacter(charactersEntity: CharactersEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(characters: List<CharactersEntity>)
}