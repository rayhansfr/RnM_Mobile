package com.example.rnmmobile.presentation.main

import android.util.Log
import com.example.core.domain.model.CharacterDomain
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.core.domain.usecase.GetUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel(private val getUseCase: GetUseCase) : ViewModel() {

    private val _characters = MutableStateFlow<List<CharacterDomain>?>(emptyList())
    val characters: LiveData<List<CharacterDomain>?> = _characters.asLiveData()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: LiveData<Boolean> = _isLoading.asLiveData()

    private val _isErr = MutableLiveData(false)
    val isErr: LiveData<Boolean> = _isErr


    init {
        fetchCharacters()
    }

    fun fetchCharacters() {
        Log.d("MainActivityViewModel", "Fetching data from API...")
        viewModelScope.launch {
            try {
                _isLoading.value = true
                _isErr.value = false

                val charList = getUseCase.execute() ?: emptyList()
                _characters.value = charList
                _isErr.value = charList.isEmpty()

            } catch (e: Exception) {
                Log.e("MainActivityViewModel", "Error fetching data: ${e.message}", e)
                _isErr.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }
}
