package com.example.application_kotlin.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json   // <-- important

@Serializable
data class CountryApiResponse(
    val cca2: String,
    val name: Name
)

@Serializable
data class Name(
    val common: String
)

object ApiClient {

    private val jsonConfig = Json {
        ignoreUnknownKeys = true       // <-- on ignore "official" et tous les champs en trop
    }

    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(jsonConfig)
        }
    }

    suspend fun fetchCountries(): List<CountryApiResponse> {
        return client.get("https://restcountries.com/v3.1/all?fields=name,cca2") {
            accept(ContentType.Application.Json)
        }.body()
    }
}
