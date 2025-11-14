package com.example.application_kotlin.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.application_kotlin.model.flagUrl
import com.example.application_kotlin.presentation.CountryUiState
import com.example.application_kotlin.presentation.CountryViewModel

@OptIn(ExperimentalMaterial3Api::class)   // <-- IMPORTANT
@Composable
fun HomeScreen(
    onCountrySelected: (String) -> Unit,
    vm: CountryViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Pays du monde") }) }
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                state.error != null -> ErrorView(state.error, vm)
                else -> CountryList(state, onCountrySelected)
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
    state: CountryUiState,
    onCountrySelected: (String) -> Unit
) {
    LazyColumn(
        Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(state.countries) { country ->
            Surface(
                tonalElevation = 1.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCountrySelected(country.code) }
            ) {
                Row(
                    Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = flagUrl(country.code),
                        contentDescription = "Drapeau de ${country.name}",
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Text(country.name)
                }
            }
        }
    }
}
