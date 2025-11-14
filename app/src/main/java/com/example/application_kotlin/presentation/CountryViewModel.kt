package com.example.application_kotlin.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.application_kotlin.model.Country
import com.example.application_kotlin.repository.CountryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class CountryUiState(
    val isLoading: Boolean = true,
    val countries: List<Country> = emptyList(),
    val error: String? = null
)

class CountryViewModel : ViewModel() {

    private val repo = CountryRepository()

    private val _state = MutableStateFlow(CountryUiState())
    val state: StateFlow<CountryUiState> = _state

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            runCatching { repo.getCountries() }
                .onSuccess {
                    _state.value = CountryUiState(
                        isLoading = false,
                        countries = it
                    )
                }
                .onFailure { e ->
                    _state.value = CountryUiState(
                        isLoading = false,
                        error = e.message
                    )
                }
        }
    }
}
