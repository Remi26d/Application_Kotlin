package com.example.application_kotlin.repository

import com.example.application_kotlin.data.ApiClient
import com.example.application_kotlin.model.Country

class CountryRepository {

    suspend fun getCountries(): List<Country> {
        val apiList = ApiClient.fetchCountries()

        return apiList
            .filter { it.cca2.isNotBlank() }
            .map { Country(it.cca2, it.name.common) }
            .sortedBy { it.name } // propre et UX friendly
    }
}
