package com.example.marvelcharacters.ui.detail

import androidx.lifecycle.*
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.usecase.characters.GetCharacterUseCase
import com.example.marvelcharacters.domain.usecase.favorites.IsFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailMainViewModel @Inject constructor(
private val getCharacterUseCase: GetCharacterUseCase,
private val isFavorites: IsFavorites
) : ViewModel() {
    val errorResponse = MutableLiveData<Throwable>()
    val successResponse = MutableLiveData<CharactersEntity>()
    val isFavoriteResponse = MutableLiveData<Boolean>()

    val onStart = MutableLiveData<Boolean>()

    fun getCharacter(id:Int) {
        viewModelScope.launch {
            getCharacterUseCase.invoke(id)
                .onStart {
                    onStart.postValue(true)
                }
                .catch { exception ->
                    errorResponse.postValue(exception)
                }
                .collect { result ->
                    successResponse.postValue(result)
                }
        }
    }

    fun isFavorite(id:Int) {
        viewModelScope.launch {
            isFavorites.invoke(id)
                .onStart {
                    onStart.postValue(true)
                }
                .catch { exception ->
                    errorResponse.postValue(exception)
                }
                .collect { result ->
                    isFavoriteResponse.postValue(result)
                }
        }
    }

    companion object {
        private const val PLANT_ID_SAVED_STATE_KEY = "characterId"
    }

}