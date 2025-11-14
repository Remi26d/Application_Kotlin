package com.example.application_kotlin.data

import com.example.application_kotlin.model.CountryInfo
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.encodeURLPath
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object WikiApiClient {

    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                }
            )
        }
    }

    @Serializable
    private data class DesktopDto(
        val page: String? = null
    )

    @Serializable
    private data class ContentUrlsDto(
        val desktop: DesktopDto? = null
    )

    @Serializable
    private data class WikiSummaryDto(
        val title: String? = null,
        val extract: String? = null,
        @SerialName("content_urls")
        val contentUrls: ContentUrlsDto? = null
    )

    suspend fun fetchCountryInfo(countryName: String): CountryInfo? {
        // on encode proprement le nom dans l’URL (espaces, accents, etc.)
        val encodedName = countryName.encodeURLPath()
        val url = "https://fr.wikipedia.org/api/rest_v1/page/summary/$encodedName"

        return try {
            val dto = client.get(url).body<WikiSummaryDto>()

            // si on n’a vraiment rien d’exploitable, on renvoie null
            if (dto.title == null && dto.extract == null) {
                null
            } else {
                CountryInfo(
                    title = dto.title ?: countryName,
                    summary = dto.extract ?: "Informations indisponibles pour ce pays.",
                    wikipediaUrl = dto.contentUrls?.desktop?.page
                )
            }
        } catch (e: Exception) {
            // en cas d’erreur réseau / format, on renvoie null
            null
        }
    }
}
