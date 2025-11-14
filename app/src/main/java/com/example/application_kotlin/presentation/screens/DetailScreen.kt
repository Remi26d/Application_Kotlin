package com.example.application_kotlin.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.application_kotlin.model.flagUrl
import com.example.application_kotlin.presentation.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    code: String,
    name: String,
    navController: NavHostController
) {
    var style by remember { mutableStateOf("flat") }
    var size by remember { mutableStateOf(64) }
    val sizes = listOf(16, 24, 32, 48, 64)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
            MaterialTheme.colorScheme.background
        )
    )

    val context = LocalContext.current
    val favVm: FavoriteViewModel = viewModel()
    val isFav by favVm.isFavorite(code).collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(name.ifBlank { "Drapeau $code" }) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Retour"
                        )
                    }
                }
            )
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(gradient)
        ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                // bouton favoris ⭐
                Button(
                    onClick = { favVm.toggleFavorite(code, name) },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isFav)
                            MaterialTheme.colorScheme.secondary
                        else
                            MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        if (isFav) "Retirer des favoris ⭐" else "Ajouter aux favoris ⭐"
                    )
                }

                Surface(
                    tonalElevation = 4.dp,
                    shape = MaterialTheme.shapes.large
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(flagUrl(code, style, size))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Drapeau $code",
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(0.85f)
                            .aspectRatio(4f / 3f)
                    )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text("Style", style = MaterialTheme.typography.titleMedium)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.wrapContentWidth()
                    ) {
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

                    Spacer(Modifier.height(8.dp))

                    Text("Taille", style = MaterialTheme.typography.titleMedium)

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        sizes.forEach { s ->
                            AssistChip(
                                onClick = { size = s },
                                label = {
                                    Text(
                                        text = "${s}px",
                                        fontWeight = if (size == s) FontWeight.SemiBold else FontWeight.Normal
                                    )
                                },
                                leadingIcon = if (size == s) {
                                    { Text("✓") }
                                } else null
                            )
                        }
                    }
                }
            }
        }
    }
}
