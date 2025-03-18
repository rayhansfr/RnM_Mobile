package com.example.core.data.repository

import com.example.core.data.local.CharactersDao
import com.example.core.data.remote.ApiService
import com.example.core.data.utils.toDomain
import com.example.core.data.utils.toEntity
import com.example.core.domain.model.CharacterDomain
import com.example.core.domain.repository.DomRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Repository (
    private val apiService: ApiService,
    private val charactersDao: CharactersDao
): DomRepository {
    override suspend fun isFavorite(charId: Int) = charactersDao.getFavoriteById(charId) != null
    override suspend fun addFavorite(characters: CharacterDomain) = charactersDao.insertFavorite(characters.toEntity())
    override fun getAllFavorite(): Flow<List<CharacterDomain>> { return charactersDao.getAllFavorites().map { list -> list.map { it.toDomain() } } }
    override suspend fun removeFavoriteById(charId: Int) = charactersDao.deleteFavoriteById(charId)
    override suspend fun fetchCharacterFromApi(): List<CharacterDomain>? = apiService.getCharList().body()?.results?.map { it.toDomain() }
    override suspend fun fetchCharacterDetail(id: Int) = apiService.getCharDetails(id).body()?.toDomain()
}