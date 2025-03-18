package com.example.core.domain.usecase

import com.example.core.domain.model.CharacterDomain
import com.example.core.domain.repository.DomRepository

class GetUseCase(private val repository: DomRepository) {
    suspend fun execute(): List<CharacterDomain>? {
        return repository.fetchCharacterFromApi()
    }
}

class GetDetailUseCase(private val repository: DomRepository) {
    suspend fun execute(id: Int): CharacterDomain? {
        return repository.fetchCharacterDetail(id)
    }
}

class FavoriteUseCase(private val repository: DomRepository) {
    suspend fun isFavorite(charId: Int) = repository.isFavorite(charId)
    suspend fun addFavorite(characterDomain: CharacterDomain) = repository.addFavorite(characterDomain)
    fun getAllFavorite() = repository.getAllFavorite()
    suspend fun removeFavoriteById(charId: Int) = repository.removeFavoriteById(charId)
}