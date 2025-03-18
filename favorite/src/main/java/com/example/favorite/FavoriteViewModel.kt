package com.example.favorite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.CharacterDomain
import com.example.core.domain.usecase.FavoriteUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FavoriteViewModel (private val favUseCase: FavoriteUseCase) : ViewModel() {

    private val _characters = MutableStateFlow<List<CharacterDomain>?>(emptyList())
    val characters: LiveData<List<CharacterDomain>?> = _characters.asLiveData()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: LiveData<Boolean> = _isLoading.asLiveData()

    private val _isErr = MutableLiveData(false)
    val isErr: LiveData<Boolean> = _isErr

    init {
        fetchFavCharacters()
    }

    fun fetchFavCharacters() {
        Log.d("MainActivityViewModel", "Fetching data from API...")
        viewModelScope.launch {
            _isLoading.value = true
            favUseCase.getAllFavorite().collect { favorites ->
                _characters.value = favorites
                _isLoading.value = false
                _isErr.value = favorites.isEmpty()
            }
        }
    }
}