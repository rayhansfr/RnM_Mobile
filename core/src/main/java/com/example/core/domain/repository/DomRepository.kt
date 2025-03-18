package com.example.core.domain.repository

import com.example.core.domain.model.CharacterDomain
import kotlinx.coroutines.flow.Flow

interface DomRepository {
    suspend fun isFavorite(charId: Int): Boolean
    suspend fun addFavorite(characters: CharacterDomain)
    fun getAllFavorite(): Flow<List<CharacterDomain>>
    suspend fun removeFavoriteById(charId: Int)
    suspend fun fetchCharacterFromApi(): List<CharacterDomain>?
    suspend fun fetchCharacterDetail(id: Int): CharacterDomain?
}