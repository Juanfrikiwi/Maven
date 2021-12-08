package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.usecase.characters.GetCharacterUseCase
import com.example.marvelcharacters.domain.usecase.favorites.IsFavoritesUseCase
import com.example.marvelcharacters.domain.usecase.favorites.AddFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val isFavorites: IsFavoritesUseCase,
    private val addFavorites: AddFavoritesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    val errorResponse = MutableLiveData<String>()
    val successResponse = MutableLiveData<CharactersEntity>()
    val addResponse = MutableLiveData<Boolean>()

    val onStart = MutableLiveData<Boolean>()
    val characterId: Int = savedStateHandle.get<Int>(CHARACTER_ID)!!
    lateinit var character: CharactersEntity
    lateinit var isFavorite: LiveData<Boolean>

    fun getCharacter(id: Int) {
        viewModelScope.launch {
            getCharacterUseCase.invoke(id)
                .onStart {
                    onStart.postValue(true)
                }
                .catch { exception ->
                    errorResponse.postValue(exception.toString())
                }
                .collect { result ->
                    character = result
                    successResponse.postValue(result)
                }
        }
    }

    fun isFavorite() {
        viewModelScope.launch {
            isFavorite = isFavorites.invoke(characterId)
        }
    }

    suspend fun addFavourite(character: CharactersEntity) {
       try {
            viewModelScope.launch {
                addFavorites.invoke(character)
            }
            addResponse.postValue(true)
        } catch (e: Exception) {
           addResponse.postValue(false)
        }
    }

    companion object {
        private const val CHARACTER_ID = "characterId"
    }

}