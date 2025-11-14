package com.example.application_kotlin.model

data class Country(
    val code: String,
    val name: String
)

// CountryFlags : tailles autorisées = 16, 24, 32, 48, 64
fun flagUrl(
    code: String,
    style: String = "flat",
    size: Int = 64      // <-- par défaut 64px (toujours valide)
): String {
    return "https://flagsapi.com/${code.uppercase()}/$style/${size}.png"
}
