package com.example.application_kotlin.presentation.screens

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.application_kotlin.model.flagUrl
import com.example.application_kotlin.presentation.CountryUiState
import com.example.application_kotlin.presentation.CountryViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onCountrySelected: (String, String) -> Unit,   // <-- code + name
    onFavoritesClick: () -> Unit,                 // <-- bouton étoile
    vm: CountryViewModel = viewModel()
) {
    val state by vm.state.collectAsState()
    var query by remember { mutableStateOf("") }

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f),
            MaterialTheme.colorScheme.background
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Pays du monde") },
                actions = {
                    IconButton(onClick = onFavoritesClick) {
                        Text("⭐")      // bouton favoris simple mais clair
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(gradient)
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))

                state.error != null -> ErrorView(state.error, vm)

                else -> Column(
                    Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = query,
                        onValueChange = { query = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Rechercher un pays ou un code") },
                        singleLine = true
                    )

                    Button(
                        onClick = {
                            val list = state.countries
                            if (list.isNotEmpty()) {
                                val random = list.random()
                                onCountrySelected(random.code, random.name)
                            }
                        },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Pays au hasard 🎲")
                    }

                    val filtered = state.countries.filter {
                        it.name.contains(query, ignoreCase = true) ||
                                it.code.contains(query, ignoreCase = true)
                    }

                    CountryList(
                        countriesState = state.copy(countries = filtered),
                        onCountrySelected = onCountrySelected
                    )
                }
            }
        }
    }
}

@Composable
fun ErrorView(error: String?, vm: CountryViewModel) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Erreur : $error")
        Spacer(Modifier.height(8.dp))
        Button(onClick = { vm.loadCountries() }) {
            Text("Réessayer")
        }
    }
}

@Composable
fun CountryList(
    countriesState: CountryUiState,
    onCountrySelected: (String, String) -> Unit
) {
    val context = LocalContext.current

    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(countriesState.countries) { country ->
            Surface(
                tonalElevation = 3.dp,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onCountrySelected(country.code, country.name)
                    }
            ) {
                Row(
                    Modifier
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(flagUrl(country.code, size = 32))
                            .crossfade(true)
                            .build(),
                        contentDescription = "Drapeau de ${country.name}",
                        modifier = Modifier.size(32.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            country.name,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            country.code,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }
    }
}
