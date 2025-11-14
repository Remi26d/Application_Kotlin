package com.example.application_kotlin.model

data class Country(
    val code: String,
    val name: String
)

// Génère l’URL du drapeau via CountryFlags
fun flagUrl(code: String, style: String = "flat", size: Int = 128): String {
    return "https://flagsapi.com/${code.uppercase()}/$style/${size}.png"
}
