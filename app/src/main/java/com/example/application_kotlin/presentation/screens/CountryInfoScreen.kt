package com.example.application_kotlin.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.application_kotlin.presentation.CountryInfoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CountryInfoScreen(
    countryName: String,
    navController: NavHostController
) {
    val vm: CountryInfoViewModel = viewModel()
    val state by vm.state.collectAsState()

    LaunchedEffect(countryName) {
        vm.load(countryName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Infos sur $countryName") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Retour")
                    }
                }
            )
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            when {
                state.isLoading -> {
                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }

                state.error != null -> {
                    Text(
                        text = state.error ?: "Erreur",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                state.info != null -> {
                    val info = state.info
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = info!!.title,
                            style = MaterialTheme.typography.headlineSmall
                        )
                        Text(
                            text = info.summary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        info.wikipediaUrl?.let { url ->
                            Text(
                                text = "Plus d'infos : $url",
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }

                else -> {
                    Text(
                        "Aucune information disponible.",
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    }
}
