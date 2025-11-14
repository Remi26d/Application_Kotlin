package com.example.application_kotlin.repository

import android.content.Context
import androidx.room.Room
import com.example.application_kotlin.data.AppDatabase
import com.example.application_kotlin.model.FavoriteCountryEntity

class FavoriteRepository(context: Context) {

    private val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "favorites_db"
    ).build()

    private val dao = db.favoriteDao()

    fun getAllFavorites() = dao.getAllFavorites()

    fun isFavorite(code: String) = dao.isFavorite(code)

    suspend fun addFavorite(code: String, name: String) {
        dao.insertFavorite(
            FavoriteCountryEntity(code = code, name = name)
        )
    }

    suspend fun removeFavorite(code: String, name: String) {
        dao.deleteFavorite(
            FavoriteCountryEntity(code = code, name = name)
        )
    }
}
