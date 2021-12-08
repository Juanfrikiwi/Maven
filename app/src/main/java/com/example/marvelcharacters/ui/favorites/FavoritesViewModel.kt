package com.example.marvelcharacters.ui.favorites

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.repository.CharactersFavouritesRepository
import com.example.marvelcharacters.domain.usecase.favorites.DeleteFavoritesUseCase
import com.example.marvelcharacters.domain.usecase.favorites.GetListFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repository: CharactersFavouritesRepository,
    private val getListFavoritesUseCase: GetListFavoritesUseCase,
    private val deleteFavoritesUseCase: DeleteFavoritesUseCase
) : ViewModel() {
    val deleteResponse = MutableLiveData<Boolean>()
    val successResponse = MutableLiveData<List<CharactersEntity>>()
    val errorResponse = MutableLiveData<Throwable>()
    val onStart = MutableLiveData<Boolean>()

    fun getListFavorites() {
        viewModelScope.launch {
            getListFavoritesUseCase.invoke()
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

    fun deleteCharacter(character: CharactersEntity) {
        viewModelScope.launch {
            deleteResponse.postValue(deleteFavoritesUseCase.invoke(character))
        }
    }
}