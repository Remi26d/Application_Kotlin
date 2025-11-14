// Fichier de build au niveau du projet
plugins {
    // Plugins déclarés via libs.versions.toml
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false

    // Plugin KSP, tel que dans la doc KSP (id + version)
    // https://kotlinlang.org/docs/ksp-quickstart.html :contentReference[oaicite:1]{index=1}
    id("com.google.devtools.ksp") version "2.1.0-1.0.29" apply false
    alias(libs.plugins.kotlin.serialization) apply false
}

