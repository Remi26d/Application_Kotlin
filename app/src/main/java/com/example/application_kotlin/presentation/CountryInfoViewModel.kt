package com.example.application_kotlin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application_kotlin.model.CountryInfo
import com.example.application_kotlin.repository.CountryInfoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountryInfoUiState(
    val isLoading: Boolean = false,
    val info: CountryInfo? = null,
    val error: String? = null
)

class CountryInfoViewModel(
    private val repo: CountryInfoRepository = CountryInfoRepository()
) : ViewModel() {

    private val _state = MutableStateFlow(CountryInfoUiState())
    val state: StateFlow<CountryInfoUiState> = _state

    fun load(countryName: String) {
        _state.value = CountryInfoUiState(isLoading = true)

        viewModelScope.launch {
            try {
                val info = repo.getCountryInfo(countryName)
                _state.value = CountryInfoUiState(
                    isLoading = false,
                    info = info,
                    error = null
                )
            } catch (e: Exception) {
                _state.value = CountryInfoUiState(
                    isLoading = false,
                    info = null,
                    error = e.message ?: "Erreur lors du chargement des informations."
                )
            }
        }
    }
}
