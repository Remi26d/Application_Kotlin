package com.example.application_kotlin.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.application_kotlin.repository.FavoriteRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {

    private val repo = FavoriteRepository(app)

    val favorites = repo.getAllFavorites()

    fun isFavorite(code: String) = repo.isFavorite(code)

    fun toggleFavorite(code: String, name: String) {
        viewModelScope.launch {
            repo.isFavorite(code).first().let { alreadyFav ->
                if (alreadyFav)
                    repo.removeFavorite(code, name)
                else
                    repo.addFavorite(code, name)
            }
        }
    }
}
