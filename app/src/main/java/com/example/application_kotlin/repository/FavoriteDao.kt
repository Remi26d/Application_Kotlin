package com.example.application_kotlin.repository

import androidx.room.*
import com.example.application_kotlin.model.FavoriteCountryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Query("SELECT * FROM favorites ORDER BY name ASC")
    fun getAllFavorites(): Flow<List<FavoriteCountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteCountryEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteCountryEntity)

    @Query("SELECT EXISTS(SELECT 1 FROM favorites WHERE code = :code)")
    fun isFavorite(code: String): Flow<Boolean>
}
