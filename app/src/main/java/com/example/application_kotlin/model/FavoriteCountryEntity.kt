package com.example.application_kotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteCountryEntity(
    @PrimaryKey val code: String,
    val name: String
)
