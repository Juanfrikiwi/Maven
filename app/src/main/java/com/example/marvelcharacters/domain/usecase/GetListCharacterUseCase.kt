package com.example.marvelcharacters.domain.usecase

import com.example.marvelcharacters.domain.models.Characters
import com.example.marvelcharacters.domain.repository.ListCharacterRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetListCharacterUseCase @Inject constructor(listCharacterRepository: ListCharacterRepository) {

}
