package com.example.marvelcharacters.ui.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.marvelcharacters.data.local.models.CharactersEntity
import com.example.marvelcharacters.domain.usecase.GetListCharactersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeMainViewModel @Inject constructor(private val getListCharactersUseCase: GetListCharactersUseCase) :
    ViewModel() {
    val successResponse = MutableLiveData<PagingData<CharactersEntity>>()
    val errorResponse = MutableLiveData<Throwable>()
    val onStart = MutableLiveData<Boolean>()

    fun getListCharacters() {
        viewModelScope.launch {
            getListCharactersUseCase.invoke().cachedIn(viewModelScope)
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

}
