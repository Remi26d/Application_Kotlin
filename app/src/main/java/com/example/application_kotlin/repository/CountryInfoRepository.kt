package com.example.application_kotlin.repository

import com.example.application_kotlin.data.WikiApiClient
import com.example.application_kotlin.model.CountryInfo

class CountryInfoRepository {

    suspend fun getCountryInfo(countryName: String): CountryInfo? {
        return WikiApiClient.fetchCountryInfo(countryName)
    }
}
