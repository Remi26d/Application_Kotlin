package com.example.application_kotlin.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.application_kotlin.model.flagUrl

@OptIn(ExperimentalMaterial3Api::class)   // <-- IMPORTANT
@Composable
fun DetailScreen(code: String) {
    var style by remember { mutableStateOf("flat") }
    var size by remember { mutableStateOf(256) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Drapeau $code") })
        }
    ) { pad ->
        Column(
            Modifier
                .padding(pad)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            AsyncImage(
                model = flagUrl(code, style, size),
                contentDescription = null,
                modifier = Modifier.size(256.dp)
            )

            Text("Style")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                FilterChip(
                    selected = style == "flat",
                    onClick = { style = "flat" },
                    label = { Text("Flat") }
                )
                FilterChip(
                    selected = style == "shiny",
                    onClick = { style = "shiny" },
                    label = { Text("Shiny") }
                )
            }

            Text("Taille")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                listOf(64, 128, 256, 512).forEach { s ->
                    AssistChip(
                        onClick = { size = s },
                        label = { Text("$s px") }
                    )
                }
            }
        }
    }
}
