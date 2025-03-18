package com.example.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(devs: CharacterEntity)

    @Query("SELECT * FROM favorite")
    fun getAllFavorites(): Flow<List<CharacterEntity>>

    @Query("SELECT * FROM favorite WHERE id = :charId")
    suspend fun getFavoriteById(charId: Int): CharacterEntity?

    @Query("DELETE FROM favorite WHERE id = :charId")
    suspend fun deleteFavoriteById(charId: Int)
}