package com.example.application_kotlin.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

// DTO venant de l'API RestCountries
@Serializable
data class CountryApiResponse(
    val cca2: String,
    val name: Name
)

@Serializable
data class Name(
    val common: String
)

// Client Ktor
object ApiClient {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    suspend fun fetchCountries(): List<CountryApiResponse> {
        return client.get("https://restcountries.com/v3.1/all?fields=name,cca2") {
            accept(ContentType.Application.Json)
        }.body()
    }
}
