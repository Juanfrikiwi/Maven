package com.example.marvelcharacters.domain.usecase.characters


import androidx.paging.PagingData
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.CharactersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetListCharactersUseCase @Inject constructor(private val charactersRepository: CharactersRepository) {
    suspend fun invoke() : Flow<PagingData<CharactersEntity>> {
        return charactersRepository.getListCharacter()
    }
}