package com.example.marvelcharacters.domain.usecase.characters


import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.models.CharacterModel
import com.example.marvelcharacters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend fun invoke(id:Int) : Flow<CharacterModel> {
        return charactersRepository.getCharacter(id)
    }
}