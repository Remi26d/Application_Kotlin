package com.example.application_kotlin.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.application_kotlin.model.FavoriteCountryEntity
import com.example.application_kotlin.repository.FavoriteDao

@Database(
    entities = [FavoriteCountryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
