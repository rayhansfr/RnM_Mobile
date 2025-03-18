package com.example.rnmmobile.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.model.CharacterDomain
import com.example.core.domain.usecase.FavoriteUseCase
import com.example.core.domain.usecase.GetDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class DetailActivityViewModel (private val getDetailUseCase: GetDetailUseCase, private val favoriteUseCase: FavoriteUseCase) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _character = MutableStateFlow<CharacterDomain?>(null)
    val character: LiveData<CharacterDomain?> = _character.asLiveData()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: LiveData<Boolean> = _isLoading.asLiveData()

    private val _isErr = MutableLiveData(false)
    val isErr: LiveData<Boolean> = _isErr

    private val _errMsg = MutableLiveData("")
    val errMsg: LiveData<String> = _errMsg

    private val _isFav = MutableStateFlow(false)
    val isFav: LiveData<Boolean> = _isFav.asLiveData()

    fun setDetailId(id: Int) {
        _id.value = id
    }

    init {
        _id.observeForever { id ->
            fetchCharacters(id)
        }
    }

    private fun fetchCharacters(id: Int) {
        Log.d("MainActivityViewModel", "Fetching data from API...")
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _isErr.value = false
                _errMsg.value = ""

                val character = getDetailUseCase.execute(id)
                if (character != null) {
                    _character.value = character
                    _isErr.value = false
                } else {
                    _isErr.value = true
                    _errMsg.value = "Failed Getting Character Detail"
                }

            } catch (e: Exception) {
                Log.e("DetailActivityViewModel", "Error fetching data: ${e.message}", e)
                _isErr.value = true
                _errMsg.value = "No internet"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun checkFavoriteStatus(devId: Int) {
        viewModelScope.launch {
            _isFav.value = favoriteUseCase.isFavorite(devId)
        }
    }

    fun toggleFavorite(char: CharacterDomain) {
        viewModelScope.launch {
            if (_isFav.value) {
                favoriteUseCase.removeFavoriteById(char.id)
            } else {
                favoriteUseCase.addFavorite(char)
            }
            checkFavoriteStatus(char.id)
        }
    }
}